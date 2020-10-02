# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Krav til innlevering

Se oblig-tekst for alle krav. Oppgaver som ikke oppfyller følgende vil ikke få godkjent:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* Ingen debug-utskrifter
* Alle testene som kreves fungerer (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Arbeidsfordeling
Oppgaven er levert av følgende student:
* Izen Asmar Nasar, S341848, s341848@oslomet.no


Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

I oppgaven har jeg hatt følgende arbeidsfordeling:
* Jeg(Asmar) har hatt hovedansvar for oppgave 1, 2, 3, 4, 5, 6, 7 og 8. 

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: Løste int antall() ved å returnere antall attributten. Sjekket boolean tom() ved å sjekke om antall = 0. Konstruktøren nullstiller alle variabler, sjekker om tabellen er tom og setter inn verdiene fra tabellen med løkker.
* Oppgave 2: Implementerte toString() ved å loope gjennom alle nodene fra hode til hale og appendet hver node.verdi i StringBuilderen. Fra hale til hode i omvendtString. leggInn(T verdi) sjekket først om listen er tom, for å  se legge inn ny node med verdi.
* Oppgave 3: 
* Oppgave 4: Implementerte indeksTil(T verdi) ved å loope gjennom alle nodene og så returnere posisjonen til node med T verdi.
* Oppgave 5: Implementerte ved å kjøre 4 if tests, som først sjekker om tabellen er tom, og så sjekker om T verdi skal plasseres, først, sist eller mellom to noder.
* Oppgave 6: fjern(int indeks) ble implementert bare ved å sjekke hvilken posisjon indeksen hadde: først, sist eller mellom. For å så fjerne noden med gitt indeks og oppdaterte pekerne neste og forrige. Fjern(T verdi) bruker samme konsept, men finner i stedet hvor verdien er lik noden, for å se fjerne noden utifra hvor den er plassert.
* Oppgave 7: Måte 1 ble implementert ved å nulle ut en node p, og en node q som blir satt p.neste før vi nuller ut alle pekerne og verdiene til p. Ble kjørt helt til vi kom til siste node, og nullet ut hode og hale. Måte 2 brukte en while-loop så lenge antallet ikke er lik null, og fjernet node for node ved hjelp av fjern(int indeks).
* Oppgave 8:

