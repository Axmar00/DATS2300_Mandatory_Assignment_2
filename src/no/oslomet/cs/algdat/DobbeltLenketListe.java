package no.oslomet.cs.algdat;

////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import java.util.Iterator;
import java.util.Objects;


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
        fratilKontroll(antall,fra,til);          //Sjekker intervallet

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
        return antall == 0;
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
        Objects.requireNonNull(verdi, "Ikke tillatt med nullverdier!");
        indeksKontroll(indeks,true);     //Lov med indeks = antall

        if(antall == 0){                             //Hvis tabellen er tom
            hode = hale = new Node<>(verdi,null,null);
        }

        else if(indeks == 0){                              //Verdien skal plasseres først
            hode = hode.forrige = new Node<>(verdi,null,hode);
        }
        else if(indeks == antall){                                  //Verdien skal plasseres bakerst
            hale = hale.neste = new Node<>(verdi,hale,null);
        }
        else{                                                      //Verdien legges mellom to noder
            Node<T> p = finnNode(indeks);                          //Verdien skal på venstre side av p
            p.forrige = p.forrige.neste = new Node<>(verdi,p.forrige,p);
        }
        endringer++;   //En endring har blitt gjort
        antall++;      //Lagt til en ny verdi -> øker antall
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    private Node<T> finnNode(int indeks){
        Node<T> p = hode;
        if(indeks < antall/2){      //Leter fra hode til indeks
            for(int i = 0; i < indeks; i++){
                p = p.neste;
            }
        }
        else{                        //Leter fra hale til indeks
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
        if(verdi == null) return false;

        Node<T> p = hode, q = null;
            for(int i = 0; i < antall; i++){
                if(verdi.equals(p.verdi)) {
                    if(p == hode){                   //Hvis hode fjernes
                        hode = hode.neste;
                    }
                    else{                            //Hvis andre verdier enn hode fjernes
                        q.neste = p.neste;
                        if(p != hale) p.neste.forrige = q;   //Hvis halen fjernes
                    }
                    if(p == hale) hale = q;          //Hvis tabellen blir tom etter fjerning

                    antall--;                        //Fjerner en verdi -> antallet blir mindre
                    endringer++;                     //Gjort en endring
                    return true;
                }
                q = p; p = p.neste;
            }
            return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);

        T temp;        //Hjelpevariabel, som skal returneres tilslutt

        if(indeks == 0){               //Hvis første verdi skal fjernes
            temp = hode.verdi;
            hode.forrige = null;
            hode = hode.neste;
            if(antall == 1) hale = null; //Hvis det kun var en verdi i tabellen
        }

        else if(indeks == antall-1){    //Hvis siste verdi skal fjernes
            temp = hale.verdi;
            hale.forrige.neste = null;
            hale = hale.forrige;
            if(antall == 1) hode = null;
        }
        else{                             //Hvis en verdi mellom hode og hale fjernes
            Node<T> p = finnNode(indeks);
            temp = p.verdi;
            Node<T> o = p.neste;
            Node<T> q = p.forrige;
            o.forrige = q;
            q.neste = o;
        }
        antall--;
        endringer++;
        return temp;
    }

    @Override
    public void nullstill() {   //Måte 1
        Node<T> p = hode;
        Node<T> q = null;

        while(p != null){          //Nuller ut nodene og dens pekere
            q = p.neste;
            p.neste = null;
            p.forrige = null;
            p.verdi = null;
            p = q;
        }
        hode = hale = null;

        antall = 0;               //Antallet settes til 0
        endringer++;
    }

    public void nullstill2(){   //Måte 2
        while(antall != 0) fjern(0);  //Kjører fjern på 0'te indeks, helt til antall = 0
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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks,false);   //Sjekker om indeksen er lovlig
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private final int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            denne = finnNode(indeks); //denne settes til noden med gitt indeks
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException("Listen har endringer");
            }
            if(!hasNext()) throw new NoSuchElementException("Ikke flere verdier");

            fjernOK = true;                 //Nå kan en verdi fjernes
            T denneVerdi = denne.verdi;     //Verdien som skal returneres
            denne = denne.neste;            //Denne-noden flyttes til neste

            return denneVerdi;              //Returnerer verdien til denne
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


