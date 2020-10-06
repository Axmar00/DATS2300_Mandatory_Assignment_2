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


Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 50 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

I oppgaven har jeg hatt følgende arbeidsfordeling:
* Jeg(Asmar) har hatt hovedansvar for oppgave 1, 2, 3, 4, 5, 6, 7 og 8. 

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: Løste int antall() ved å returnere antall-attributten. Sjekket boolean tom() ved å sjekke om antall = 0. Konstruktøren nullstiller alle variabler, sjekker om tabellen er tom og setter inn verdiene fra tabellen med løkker.

* Oppgave 2a: Implementerte toString() ved å loope gjennom alle nodene fra hode til hale og appendet hver node.verdi i StringBuilderen. Gjorde det samme i omvendtString, bare at loopen gikk fra hale til hode. 
* Oppgave 2b: Implementerte leggInn(T verdi) ved å først ikke tillate null-verdier med Objects.requireNonNull. Deretter sjekkes det om lista er tom eller ikke ved if-else tester. Hvis den er tom settes hole og hade lik den nye node-verdien. Hvis ikke, settes noden bakerst og hale sin peker endres til den nye noden. Returner true hvis verdien ble lagt inn, og false ellers.

* Oppgave 3a: Implementerte finnNode(int indeks) ved å å sjekke med en if-else test om indeksen ligger nærmest hode eller hale. Hvis den er nærmest hode loopes det fra hode til indeks hvor p settes lik p.neste. Hvis den er i midten eller nærmest hale, skjer det samme som i første tilfelle bare at det loopes fra hale til indeks. Til slutt returneres noden p.
* Oppgave 3b: Implementerte subliste-metoden ved å først sjekke om intervallet var gyldig(fratilkontroll) og så istansiere et Liste objekt. Deretter loopes det fra "fra" til "til" og så legges inn node-verdiene i liste-objektet ved hjelp av leggInn(finnNode(i).verdi). Så returneres listen.

* Oppgave 4: Implementerte indeksTil(T verdi) ved å først sjekke om T verdi er lik null, og i så fall vil det returneres -1. Deretter loopes det gjennom alle nodene med en for-løkke, men loopen vil stoppe dersom med følgende if-test inni loopen: if(verdi.equals(p.verdi)). Når T verdi blir lik en av node-verdiene, returneres posisjonen til noden. Hvis den ikke blir funnet returneres -1.

* Oppgave 5: Implementerte ved å kjøre 4 if tests, som først sjekker om tabellen er tom, og så sjekker om T verdi skal plasseres, først, sist eller mellom to noder.

* Oppgave 6: fjern(int indeks) ble implementert bare ved å sjekke hvilken posisjon indeksen hadde: først, sist eller mellom. For å så fjerne noden med gitt indeks og oppdaterte pekerne neste og forrige. Fjern(T verdi) bruker samme konsept, men finner i stedet hvor verdien er lik noden, for å se fjerne noden utifra hvor den er plassert.

* Oppgave 7: Måte 1 ble implementert ved å nulle ut en node p, og en node q som blir satt p.neste før vi nuller ut alle pekerne og verdiene til p. Ble kjørt helt til vi kom til siste node, og nullet ut hode og hale. Måte 2 brukte en while-loop så lenge antallet ikke er lik null, og fjernet node for node ved hjelp av fjern(int indeks). Fant ut at metode 1 var den raskeste, ved å teste i main metoden ved å legge til flere tusen verdier, og tilslutt kjøre begge nullstill(). Metode 2 brukte lengst tid.

* Oppgave 8a:
* Oppgave 8b:
* Oppgave 8c:
* Oppgave 8d:
