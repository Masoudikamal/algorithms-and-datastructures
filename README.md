# Algorithms & Data Structures — Sorting (Java)

Dette repoet presenterer en eksamensoppgave i **algoritmer og datastrukturer** der flere sorteringsalgoritmer er implementert i Java og sammenlignet.  
Målet er å vise **tydelig kode**, **kortfattet dokumentasjon**, og **hvordan man kjører** algoritmene lokalt. En fullstendig rapport ligger ved i PDF.


---

## Innhold

- `src/`
  - `BubbleSort.java`
  - `InsertionSort.java`
  - `MergeSort.java`
  - `QuickSort.java`
- `Algorithms-DSA-Report.pdf` – rapport med teori, metode, resultater og kilder
- `.gitignore`
- `README.md`

---

## Kort oppsummering

- Implementerte **Bubble**, **Insertion**, **Merge** og **Quick** i Java.
- Sammenlignet algoritmene på samme datasett (detaljer og tall i PDF).
- Drøftet **empiri vs. teori** (tidskompleksitet, minneforbruk, beste/verste snitt).
- La vekt på *lesbar kode* og enkel kjøring (ingen eksterne biblioteker).

---

## Algoritmene — Big-O og egenskaper

| Algoritme       | Beste tid | Snitt       | Verste      | Plass | Kommentar |
|-----------------|-----------|-------------|-------------|-------|-----------|
| Bubble Sort     | O(n)      | O(n²)       | O(n²)       | O(1)  | Meget enkel; tidlig avbrudd hvis allerede sortert |
| Insertion Sort  | O(n)      | O(n²)       | O(n²)       | O(1)  | God på små eller delvis sorterte datasett |
| Merge Sort      | O(n log n)| O(n log n)  | O(n log n)  | O(n)  | Stabil, forutsigbar; ikke in-place (bruker ekstra buffer) |
| Quick Sort      | O(n log n)| O(n log n)  | O(n²)       | O(log n) | Svært rask i praksis; pivotstrategi avgjør ytelse |

> Rapporten går dypere i metoden (valg av datasett, måleoppsett) og diskuterer hvorfor resultatene samsvarer med klassisk kompleksitetsteori.

---

## Hvordan kjøre

> **Krav:** Java 17 eller nyere anbefales (Java 21 fungerer fint).

### A) IntelliJ IDEA (anbefalt)
1. Åpne prosjektmappen i IntelliJ.
2. Sett **Project SDK** til Java 17/21 (File → Project Structure).
3. Kjør algoritmene:
   - Hvis filene har `public static void main(String[] args)`, høyreklikk filen (f.eks. `MergeSort.java`) og velg **Run**.
   - Hvis du bruker en egen *benchmark/driver-klasse*, kjør den (f.eks. `Benchmark`).

### B) Kommandolinje (Windows PowerShell)
```powershell
# fra prosjektroten
mkdir bin
javac -d bin src\*.java

# Kjør en klasse som har main (bytt til riktig klassenavn hvis nødvendig)
java -cp bin BubbleSort
java -cp bin InsertionSort
java -cp bin MergeSort
java -cp bin QuickSort
```

### (Valgfritt) macOS/Linux
```bash
mkdir -p bin
javac -d bin src/*.java
java -cp bin MergeSort
```

> **Tips:** Har du en egen `Benchmark.java` som sammenligner algoritmene, kompiler den også og kjør `java -cp bin Benchmark`.

---

## Mappestruktur (forventet)

```
.
├─ src/
│  ├─ BubbleSort.java
│  ├─ InsertionSort.java
│  ├─ MergeSort.java
│  └─ QuickSort.java
├─ Algorithms-DSA-Report.pdf
├─ .gitignore
└─ README.md
```

> Eventuelle IDE-mapper (`.idea/`, `*.iml`) og bygge-artefakter (`out/`, `bin/`, `target/`) ignoreres av `.gitignore`.

---

## Hva jeg gjorde (prosjektpresentasjon)

- **Implementasjon:** Skrev rene, selvstendige implementasjoner av de fire algoritmene i Java.
- **Test & sammenligning:** Kjørte på samme datasett/parametre for å kunne sammenligne på en rettferdig måte.
- **Analyse:** Sammenholdt resultatene med forventet **O-notasjon** og diskuterte avvik/observasjoner (f.eks. pivotvalg i Quick Sort).
- **Dokumentasjon:** Oppsummerte funn og metode i PDF, og presenterte prosjektet kortfattet i denne README-en.

---

## Videre arbeid (idéer)

- Legg til en enkel **`Benchmark.java`** som:
  - Genererer forskjellige datasett (tilfeldig, sortert, delvis sortert).
  - Måler tid med `System.nanoTime()` og summerer antall sammenligninger/flyttinger/merges (om du teller disse i kodene).
  - Skriver en tabell til konsollen (algoritme, n, tid, antall operasjoner).

- Legg til en liten **datasample** dersom en algoritme leser fra fil (unngå store filer i repo).  
- **CI med GitHub Actions** som kompilasjonssjekker `src/` ved push/PR.
- En **tabell i README** som viser måleresultater fra din maskin (n, ms, ev. antall operasjoner).

---

## Lisens

MIT

---
