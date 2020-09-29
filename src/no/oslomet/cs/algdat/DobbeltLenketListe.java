package no.oslomet.cs.algdat;

////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        this();    //Nullstiller alle variablene, ved å kalle på konstruktøren DobbeltLenketListe()

        Objects.requireNonNull(a, "Tabellen er null!");  //Tabellen kan ikke være null

        if(a.length != 0) {                                      //Sjekker om tabellen er tom
            int i = 0;
            for (; i < a.length && a[i] == null; ++i);           //Looper for å finne første ikke-nullverdi
            if(i == a.length) return;                            //returnerer tom tabell, hvis kun nullverdier

            Node<T> p = hode = new Node<>(a[i], null, null);   //Setter hode = første ikke-nullverdi
            antall = 1;

            for (i++; i < a.length; i++) {                          //looper gjennom og legger til ikke-nullverdier
                if (a[i] != null) {                                 // i nodene.
                    p = p.neste = new Node<>(a[i], p, null);
                    antall++;                                       //antallet økes med hver node som lages
                }
            }
            hale = p;                                              //Setter hale lik siste noden
        }
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(antall,fra,til);          //Sjekker indeksen

        Liste<T> list = new DobbeltLenketListe<>();
        for(int i = fra; i < til; i++){
            list.leggInn(finnNode(i).verdi);         //Legger inn verdiene <fra:til) i listen
        }
        return list;
    }

    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if(antall == 0) return true;
        return false;
    }

    @Override
    public boolean leggInn(T verdi) {
       Objects.requireNonNull(verdi,"Ikke tillatt med nullverdier!");

       if(antall == 0) hode = hale = new Node<>(verdi,null,null);  //tom liste -> hode og hale = nye noden
       else hale = hale.neste = new Node<>(verdi,hale,null);             //ikke tom -> legges bakerst

       antall++;          //Ny verdi i listen -> antallet har økt
       endringer++;       //Endring har blitt gjort
       return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    private Node<T> finnNode(int indeks){
        Node<T> p = hode;
        if(indeks < antall/2){
            for(int i = 0; i < indeks; i++){
                p = p.neste;
            }
        }
        else{
            p = hale;
            for(int i = antall-1; i > indeks; i--){
                p = p.forrige;
            }
        }
        return p;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if(verdi == null) return -1;  //Hvis verdi er null

        Node<T> p = hode;
        for(int i = 0; i < antall; i++){     //Kjører gjennom nodene
            if(verdi.equals(p.verdi)) return i;   //Sjekker om node-verdien er lik verdi, returnerer indeksen
            p = p.neste;                     //Går til neste node
        }
        return -1;                           //Hvis verdi ikke ble funnet
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi,"Nullverdier ikke tillatt");
        indeksKontroll(indeks,false);

        Node<T> gammelNode = finnNode(indeks);   //Finner noden med gitt indeks
        T gammelVerdi = gammelNode.verdi;        //Lagrer gamle nodens verdi
        gammelNode.verdi = nyverdi;              //Oppdaterer gammel verdi med nyVerdi
        endringer++;                             //En endring har blitt gjort
        return gammelVerdi;                      //Returnerer gammel verdi
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");

        if(antall != 0) {               //Hvis listen ikke er tom
            Node<T> p = hode;
            s.append(p.verdi);
            p = p.neste;

            for (int i = 1; i < antall; i++) {
                s.append(",").append(" ").append(p.verdi);
                p = p.neste;
            }
        }

        s.append("]");
        return s.toString();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();
        s.append("[");

        if(antall != 0) {      //Hvis listen ikke er tom
            Node<T> p = hale;
            s.append(p.verdi);
            p = p.forrige;

            for (int i = antall-1; i > 0; i--) {
                s.append(",").append(" ").append(p.verdi);
                p = p.forrige;
            }
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {


    }

} // class DobbeltLenketListe


