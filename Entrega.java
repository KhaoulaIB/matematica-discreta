
import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:Khaoula Ikkene
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
    /*
     * Aquí teniu els exercicis del Tema 1 (Lògica).
     *
     * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
     * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
     * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
     * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
     * similarment s'avaluen com `p.test(x, y)`.
     *
     * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
     * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
     * petit com per poder provar tots els casos que faci falta).
     */
    static class Tema1 {
        /*
         * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
         */
        static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
            int CasosY=0;
            for (int x: universe){
                for (int y: universe){
                    if(p.test(x) && !q.test(x,y)){ // el cas en qué la implicacio és falsa 1-->0
                        CasosY = 0;
                    }else{
                        CasosY++;
                    }
                }
            }
            return CasosY == 1; // cert si per ∀x exiteix un Y única
        }

        /*
         * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
            int contadorx = 0;
            for (int x : universe) {
                boolean NoImplica = false;
                for (int y : universe) {
                    if (p.test(y) && !q.test(x, y)) { // en cas que no es compleix la implicació
                        NoImplica = true;
                        break;
                    }
                }
                if (!NoImplica) {
                    contadorx++;
                }
                 /*   if (contadorx > 1) {// si la x no és única
                        return false;
                    }
                }*/
            }
            return contadorx == 1;
        }


        /*
         * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
         */
        static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
            int CasosZ = 0;
            int z = 0;
            for (int i : universe) {
                for (int j : universe) {
                    while (z<universe.length){
                        if ((p.test(i, z) && !(q.test(j, z))) || (!p.test(i, z) && (q.test(j, z)))) { // donat que p ⊕ q <=> (¬p /\q)\/  (p /\¬q)
                            CasosZ++; // es cumpleix per z
                            z++;
                        }
                        break;
                    }
                }
            }
            return CasosZ==universe.length;
        }

        /*
         * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
         */
        static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
            //estudiarem només el cas en què no es compleix la implicació, ja que pels altres casos certa la implicació
            int casosXp = 0;
            int casosXq = 0;

            for (int i : universe) {
                if (p.test(i)) {
                    casosXp++; // comprovar els casos de P(x)
                }
            }
            for (int i : universe) {
                if (q.test(i)) {
                    casosXq++; //comprovar els casos de Q(x)
                }
            }
            // implicació false en cas de ser 1->0
            return !((casosXp == universe.length ) &&  (casosXq != universe.length));

        }

        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // ∀x ∃!y. P(x) -> Q(x,y) ?

            assertThat(
                    exercici1(
                            new int[] { 2, 3, 5, 6 },
                            x -> x != 4,
                            (x, y) -> x == y
                    )
            );

            assertThat(
                    !exercici1(
                            new int[] { -2, -1, 0, 1, 2, 3 },
                            x -> x != 0,
                            (x, y) -> x * y == 1
                    )
            );

            // Exercici 2
            // ∃!x ∀y. P(y) -> Q(x,y) ?

            assertThat(
                    exercici2(
                            new int[] { -1, 1, 2, 3, 4 },
                            y -> y <= 0,
                            (x, y) -> x == -y
                    )
            );

            assertThat(
                    !exercici2(
                            new int[] { -2, -1, 1, 2, 3, 4 },
                            y -> y < 0,
                            (x, y) -> x * y == 1
                    )
            );

            // Exercici 3
            // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

            assertThat(
                    exercici3(
                            new int[] { 2, 3, 4, 5, 6, 7, 8 },
                            (x, z) -> z % x == 0,
                            (y, z) -> z % y == 1
                    )
            );

            assertThat(
                    !exercici3(
                            new int[] { 2, 3 },
                            (x, z) -> z % x == 1,
                            (y, z) -> z % y == 1
                    )
            );

            // Exercici 4
            // (∀x. P(x)) -> (∀x. Q(x)) ?

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    )
            );

            assertThat(
                    !exercici4(
                            new int[] { 0, 2, 4, 6, 8, 16 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    )
            );
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 2 (Conjunts).
     *
     * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
     * conjunt de conjunts d'enters tendrà tipus int[][].
     *
     * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
     * només té dos elements. Per exemple
     *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
     * i també donarem el conjunt on està definida, per exemple
     *   int[] a = {0,1,2};
     *
     * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
     * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
     * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
     */
    static class Tema2 {
        /*
         * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
         *
         * Podeu soposar que `a` està ordenat de menor a major.
         */
        static boolean exercici1(int[] a, int[][] rel) {
            // una relació és d'equivalència si és: Reflexiva, Simètrica i transitiva.
            Boolean reflexiva =  EsReflexiva(a,rel);
            Boolean simetrica = EsSimetrica (a,rel);
            Boolean transtiva = EsTransitiva(a,rel);


            return reflexiva && simetrica && transtiva;
        }

        public static boolean EsReflexiva(int[]a,int [][]rel){
            //Perquè rel sigui reflexiva tots els seus elements han d'estar relacionats amb ells mateixos
            int contador=0;
            for (int[] element : rel) {
                if (element[0] == element[1]) {
                    contador++;
                }

            }
            return contador == a.length;
        }

        public static boolean EsSimetrica(int[]a,int [][]rel){
            // rel será simetrica si∀ b, c ∈ a si b R c → c R b
            int contador=0;
            for (int[] b : rel) {
                for (int[] c : rel) {
                    if ((b[1] == c[0]) && (b[0] == c[1])) {
                        contador++;
                    }
                }
            }
            return contador == rel.length;
        }

        public static boolean EsTransitiva(int[] a, int[][] rel) {
            // uan relació és transitiva si ∀ b, c, d : b R c ∧ c R d → b R d
            for (int[] b : rel) {
                for (int[] c : rel) {
                    for (int[] d : rel) {
                        if (b[1] == c[0] && c[1] == d[0]) {
                            if (!ExisteixRelacio(rel, b[0], d[1])) { // verifica que la parella (b,d) existeix en rel
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }

        /*
        *Metode que verifica que una parella que es passa per parametre (b,d) està en la relació rel
         */
        public static boolean ExisteixRelacio(int[][] rel, int b, int d) {
            for (int[] parella : rel) {
                if (parella[0] == b && parella[1] == d) {
                    return true;
                }
            }
            return false;
        }


        /*
         * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
         * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
         *
         * Podeu soposar que `a` està ordenat de menor a major.
         */
        /*
        * Donat que el conjunt quocient conté les classes d'equivalencies d'una relacio definida sobre un conjunt d'elements
        * calcularem aquestes classes i la seva quantitat será i el cardinal del conjunt quociente
         */

      static int exercici2(int[] a, int[][] rel) {
          if (!exercici1(a, rel)) {
              return -1;
          } else {
              List<Set<Integer>> clasesEquivalencia = new ArrayList<>();
              Set<Integer> elementsProcessats = new HashSet<>();

              for (int[] pareja : rel) {
                  int elementoA = pareja[0];
                  int elementoB = pareja[1];

                  Set<Integer> claseExistenteA = null;
                  Set<Integer> claseExistenteB = null;

                  // cercar les classes d'equivalencia existents
                  for (Set<Integer> clase : clasesEquivalencia) {
                      if (claseExistenteA == null && clase.contains(elementoA)) {
                          claseExistenteA = clase;
                      }
                      if (claseExistenteB == null && clase.contains(elementoB)) {
                          claseExistenteB = clase;
                      }
                      if (claseExistenteA != null && claseExistenteB != null) {
                          break;
                      }
                  }
                // Si un element no té classe s'afegeix a una nova
                  if (claseExistenteA == null && claseExistenteB == null) {
                      Set<Integer> nuevaClase = new HashSet<>();
                      nuevaClase.add(elementoA);
                      nuevaClase.add(elementoB);
                      clasesEquivalencia.add(nuevaClase);
                  } else if (claseExistenteA == null) {
                      // Si l'element A no està en una classe existent, s'afegeix al conjunt de la classe de B
                      claseExistenteB.add(elementoA);
                  } else if (claseExistenteB == null) {
                      // Si l'element B no està en una classe existent, s'afegeix al conjunt de la classe de A
                      claseExistenteA.add(elementoB);
                  } else if (claseExistenteA != claseExistenteB) {
                      // si els mateixos elements estan en diferents classes es fusionen les seves classes a la Classe A
                      // La classe B s'elemina del conjunt de classes d'equi.
                      claseExistenteA.addAll(claseExistenteB);
                      clasesEquivalencia.remove(claseExistenteB);
                  }

                  elementsProcessats.add(elementoA);
                  elementsProcessats.add(elementoB);
              }
                    //Calcular el nombre de classes d'equivalència restants
              int numClasesEquivalencia = a.length - elementsProcessats.size();
              return clasesEquivalencia.size() + numClasesEquivalencia;
          }
      }



        /*
         * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
         *
         * Podeu soposar que `a` i `b` estan ordenats de menor a major.
         */

        /*
        *  Una relacio es una funció si  a cada valor de a li asigna un únic valor de b

         */
        static boolean exercici3(int[] a, int[] b, int[][] rel) {
            int contador =0;
            int[] asignaciones = new int[a.length];
            for (int i =0; i<a.length; i++){
                for (int k : b) {
                    for (int[] ints : rel) {
                        if ((a[i] == ints[0]) && (k == ints[1])) {
                            asignaciones[i] = 1;
                        }
                    }
                }
            }
            for (int asignacion : asignaciones) {
                if (asignacion == 1) { // contar el número d'elements del conjunt a que tenen una única imatge al conjunt a
                    contador++;
                }
            }
            return contador == a.length;
        }


        /*
         * Suposem que `f` és una funció amb domini `dom` i codomini `codom`. Retorna:
         * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
         * - Si no ho és, però és injectiva, la diferència entre el cardinal de la imatge de `f` i el cardinal de `codom`.
         * - En qualsevol altre cas, retorna 0.
         *
         * Suposem que `dom` i `codom` estan ordenats de menor a major.
         */
        static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
            int numElements = dom.length;
            int[] imatges = new int[numElements]; // nombre d'imatges de cada element

            for (int i = 0; i < numElements; i++) {
                imatges[i] = f.apply(dom[i]);
            }

            // Comprovar si és exhaustiva
            boolean exhaustiva = (imatges.length == codom.length);

            // Comprovar si és injectiva -> tot element té !imatge
            Set<Integer> conjuntImatges = new HashSet<>();
            for (int imatge : imatges) {
                conjuntImatges.add(imatge);
            }
            boolean injectiva = (conjuntImatges.size() == dom.length);

            if (exhaustiva) {
                // Càlcul del màxim cardinal de l'antiimatge
                int maxCardinal = 0;
                for (int element : codom) {
                    int cardinal = 0;
                    for (int imatge : imatges) {
                        if (imatge == element) {
                            cardinal++;
                        }
                    }
                    if (cardinal > maxCardinal) {
                        maxCardinal = cardinal;
                    }
                }
                return maxCardinal;
            } else if (injectiva) {
                // Càlcul de la diferència entre el cardinal de la imatge i el cardinal de `codom`
                return conjuntImatges.size() - codom.length;
            } else {
                return 0;
            }
        }


        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // `rel` és d'equivalencia?

            assertThat(
                    exercici1(
                            new int[] { 0, 1, 2, 3 },
                            new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
                    )
            );

            assertThat(
                    !exercici1(
                            new int[] { 0, 1, 2, 3 },
                            new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
                    )
            );

            // Exercici 2
            // si `rel` és d'equivalència, quants d'elements té el seu quocient?

            final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

            assertThat(
                    exercici2(
                            int09,
                            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
                    )
                            == 3
            );

            assertThat(
                    exercici2(
                            new int[] { 1, 2, 3 },
                            new int[][] { {1, 1}, {2, 2} }
                    )
                            == -1
            );

            // Exercici 3
            // `rel` és una funció?

            final int[] int05 = { 0, 1, 2, 3, 4, 5 };

            assertThat(
                    exercici3(
                            int05,
                            int09,
                            generateRel(int05, int09, (x, y) -> x == y)
                    )
            );

            assertThat(
                    !exercici3(
                            int05,
                            int09,
                            generateRel(int05, int09, (x, y) -> x == y/2)
                    )
            );

            // Exercici 4
            // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
            // sino, |im f| - |codom| si és injectiva
            // sino, 0

            assertThat(
                    exercici4(
                            int09,
                            int05,
                            x -> x / 4
                    )
                            == 0
            );

            assertThat(
                    exercici4(
                            int05,
                            int09,
                            x -> x + 3
                    )
                            == int05.length - int09.length
            );

            assertThat(
                    exercici4(
                            int05,
                            int05,
                            x -> (x + 3) % 6
                    )
                            == 1
            );
        }

        /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
        static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
            ArrayList<int[]> rel = new ArrayList<>();

            for (int a : as) {
                for (int b : bs) {
                    if (pred.test(a, b)) {
                        rel.add(new int[] { a, b });
                    }
                }
            }

            return rel.toArray(new int[][] {});
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 3 (Grafs).
     *
     * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
     * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
     * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
     *
     *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
     *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
     *
     * Podeu suposar que cap dels grafs té llaços.
     */
    static class Tema3 {
        /*
         * Retornau l'ordre menys la mida del graf (no dirigit).
         */
        static int exercici1(int[][] g) {
            int NumArestes = 0;
            for (int[] ints : g) {
                NumArestes += ints.length;
            }
            return g.length - (NumArestes / 2); // Es un graf no dirigit i hem comptat les arestes 2 vegades

        }
        /*
         * Suposau que el graf (no dirigit) és connex. És bipartit?
         */
        static boolean exercici2(int[][] g) {
            int n = g.length;
            int[] colors = new int[n];
            Arrays.fill(colors, -1); // Inicialitzar tots els nodes amb color no assignat

            for (int i = 0; i < n; i++) {
                if (colors[i] == -1) { // Assignar color als nodes sense color
                    if (!bfsBipartit(g, i, colors)) {
                        return false; // Si es troba un cicle imparell, el graf no és bipartit
                    }
                }
            }

            return true; // Si no es troba cap cicle imparell, el graf és bipartit
        }

        static boolean bfsBipartit(int[][] g, int inici, int[] colors) {
            int n = g.length;
            int[] cua = new int[n];
            int front = 0;
            int rear = 0;

            cua[rear++] = inici;
            colors[inici] = 0; // Assignar el color 0 al node d'inici

            while (front != rear) {
                int node = cua[front++];
                front = front % n;

                for (int veí : g[node]) {
                    if (colors[veí] == -1) { // Si el veí no té un color assignat, assignar-li el color oposat al del node actual
                        colors[veí] = 1 - colors[node];
                        cua[rear++] = veí;
                        rear = rear % n;
                    } else if (colors[veí] == colors[node]) { // Si el veí té el mateix color que el node actual, s'ha trobat un cicle imparell
                        return false;
                    }
                }
            }

            return true;
        }



        /*
         * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
         * vèrtex i-èssim.
         */
        static int  exercici3(int[][] g, int i) {
            ArrayList <Integer> Vertexs = new ArrayList<>(g[i].length);
            int NoumDescendents = 0;
                for (int j = 0; j < g[i].length; j++) { // ens posicionem en la fila corresponent
                    Vertexs.add(g[i][j]);
                }

            for (Integer vertex : Vertexs) { // conté els vertexos adjacents amb el vertex i
                if (g[vertex].length == 0) { // longitud de vertex = 0 -> grau de la seva sortida = 0
                    NoumDescendents++;
                } else {
                    NoumDescendents = exercici3(g, vertex);
                }
            }

            return NoumDescendents;
        }




        /*
         * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
         * del graf subjacent. Suposau que totes les arestes tenen pes 1.
         */
        public static int exercici4(int [][] g){
            ArrayList <Integer> distancies = new ArrayList<>(g.length);
            for (int i = 0; i<g.length; i++){
                for (int j =0; j<g.length; j++){
                    distancies.add(CamiMesCurt(g, i,j) );
                }

            }
            int maxDistance = distancies.get(0);
            for (int i = 1; i < distancies.size(); i++) {
                int distance = distancies.get(i);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

            return maxDistance;
        //    return distancies.stream().mapToInt()
        }


        public static int CamiMesCurt(int[][] g, int fulla1, int fulla2) {
            int n = g.length;
            if (n == 0 || fulla1 < 0 || fulla1 >= n || fulla2 < 0 || fulla2 >= n) return -1;

            int[] distances = new int[n];
            Arrays.fill(distances, -1);

            // Trobar el node pare comú més proper
            int commonAncestor = trobarAncestreComu(g, fulla1, fulla2);

            int distanceFromLeaf1ToAncestor = CalcularDistancia(g, fulla1, commonAncestor);

            // Calcular la distancia desde leaf2 hasta el nodo padre común
            int distanceFromLeaf2ToAncestor = CalcularDistancia(g, fulla2, commonAncestor);

            // Calcular la distancia total sumando las distancias parciales
            int shortestDistance = distanceFromLeaf1ToAncestor + distanceFromLeaf2ToAncestor;

            return shortestDistance;
        }

        // Función auxiliar para encontrar el nodo padre común más cercano
        private static int trobarAncestreComu(int[][] graph, int fulla1, int fulla2) {
            Set<Integer> ancestors = new HashSet<>();

            // Afegeix tots els ancestres de fulla1 al conjunt
            int fullaActual = fulla1;
            while (fullaActual != 0) {
                ancestors.add(fullaActual);
                fullaActual = trobarPare(graph, fullaActual);
            }

            // Comprovar si fulla2 o algun dels seus ancestres està en el conjunt
            fullaActual = fulla2;
            while (fullaActual != 0) {
                if (ancestors.contains(fullaActual)) {
                    return fullaActual;
                }
                fullaActual = trobarPare(graph, fullaActual);
            }

            return 0; // No tenen cap pare comú
        }

        // Funció auxiliar per trobar el pare d'un node al graf

        private static int trobarPare(int[][] graph, int node) {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[i].length; j++) {
                    if (graph[i][j] == node) {
                        return i;
                    }
                }
            }
            return -1; // No s'ha trobat cap pare
        }

        // Funció auxiliar per calcular la distància entre dos nodes al graf

        private static int CalcularDistancia(int[][] graph, int start, int end) {
            int distancia = 0;
            int fullaActual = start;

            while (fullaActual != end) {
                int pare = trobarPare(graph, fullaActual);

                if (pare == -1) {
                    return -1;
                }

                distancia++;
                fullaActual = pare;
            }

            return distancia;
        }


        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            final int[][] undirectedK6 = {
                    { 1, 2, 3, 4, 5 },
                    { 0, 2, 3, 4, 5 },
                    { 0, 1, 3, 4, 5 },
                    { 0, 1, 2, 4, 5 },
                    { 0, 1, 2, 3, 5 },
                    { 0, 1, 2, 3, 4 },
            };

      /*
         1
      4  0  2
         3
      */
            final int[][] undirectedW4 = {
                    { 1, 2, 3, 4 },
                    { 0, 2, 4 },
                    { 0, 1, 3 },
                    { 0, 2, 4 },
                    { 0, 1, 3 },
            };

            // 0, 1, 2 | 3, 4
            final int[][] undirectedK23 = {
                    { 3, 4 },
                    { 3, 4 },
                    { 3, 4 },
                    { 0, 1, 2 },
                    { 0, 1, 2 },
            };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
            final int[][] directedG1 = {
                    { 1, 2 }, // 0
                    { 3 },    // 1
                    { 3, 8 }, // 2
                    { 4 },    // 3
                    { 5, 6 }, // 4
                    {},       // 5
                    {},       // 6
                    { 0 },    // 7
                    {},
            };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

            final int[][] directedRTree1 = {
                    { 1, 2, 3 }, // 0 = r
                    {},          // 1
                    { 4, 5 },    // 2
                    { 6 },       // 3
                    { 7, 8 },    // 4
                    {},          // 5
                    {},          // 6
                    {},          // 7
                    {},          // 8
            };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

            final int[][] directedRTree2 = {
                    { 1 },
                    { 2, 3 },
                    {},
                    { 4, 5 },
                    {},
                    { 6, 7 },
                    {},
                    {},
            };

            assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
            assertThat(exercici1(undirectedW4) == 5 - 2*4);

            assertThat(exercici2(undirectedK23));
            assertThat(!exercici2(undirectedK6));

            assertThat(exercici3(directedG1, 0) == 3);
            assertThat(exercici3(directedRTree1, 2) == 3);

            assertThat(exercici4(directedRTree1) == 5);
            assertThat(exercici4(directedRTree2) == 4);
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 4 (Aritmètica).
     *
     * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
     * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
     */
    static class Tema4 {
        /*
         * Donau la solució de l'equació
         *
         *   ax ≡ b (mod n),
         *
         * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
         *
         * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */


        static int[] exercici1 ( int a, int b, int n){
            //Té solució si b% mcd(a,n)=0
            int d = MCDEuclides(a, n);
            int[] solucio = new int[2];
            solucio[1] = n;
            int factor = b / d;

            if (b % d == 0) {
                int temp [] = AlgEuclidesEstes(a, n);
                solucio[0] = temp[0]* factor;
                while (solucio[0] < 0) { //
                    solucio[0] += n;
                    // canviar el resultat a un nombre positiu

                }
                if (solucio[0] != 0 && solucio[1] % solucio[0] == 0) { // Simplificar el resultat
                    solucio[1] = solucio[1] / solucio[0];
                }
                return solucio;
            } else {
                return null; // no té solució
            }

        }



        static public int [] AlgEuclidesEstes ( int a, int b){
            int x = 0;
            int y = 1;
            int darrerX = 1;
            int darrerY = 0;
            int temp;
            while (b != 0) {
                int q = a / b;
                int r = a % b;

                a = b;
                b = r;

                temp = x;
                x = darrerX - q * x;
                darrerX = temp;

                temp = y;
                y = darrerY - q * y;
                darrerY = temp;
            }

            return new int [] {darrerX, darrerY};

        }


// Metode auxiliar per trobar el mcd de dos numeros pel metode d'Euclides
        public static int MCDEuclides(int a, int n) {
            if (a == 0) {
                return 1;
            }
            // intercanvi per treballar sempre amb el numero més gran
            if (a < n) {
                int temp = a;
                a = n;
                n = temp;
            }

            int residu = 0;

            while (n != 0) {
                int x = a / n;
                residu = a - (x * n);
                a = n;
                n = residu;
            }

            return a;
        }


        //metode per calcular l'invers d'un numero
        public static int Invers ( int a, int modul){
            int invers = 0;
            for (int i = 1; i < modul; i++) {
                if ((a * i) % modul == 1) {
                    invers = i;
                }
            }
            // Per compensar si l'invers és negatiu
            while (invers<0){
                invers +=modul;
            }

            return invers;
        }

        /*
         * Donau la solució (totes) del sistema d'equacions
         *
         *  { x ≡ b[0] (mod n[0])
         *  { x ≡ b[1] (mod n[1])
         *  { x ≡ b[2] (mod n[2])
         *  { ...
         *
         * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
         * que els dos arrays tenen la mateixa longitud.
         *
         * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */
        static int[] exercici2a ( int[] b, int[] n) {

            //   int[] solucio = new int[2];
            int c = 0;
            int[] P = new int[n.length];
            int[] Q = new int[n.length];
            int N = 1;
            if (TeSolpelTXR(n)) {
                for (int i = 0; i < n.length; i++) {
                    N *= n[i]; // N = n1*n2*n3...
                }
                for (int i = 0; i < P.length; i++) {
                    P[i] = N / n[i]; // P1,P2,..PN
                }

                for (int i = 0; i < P.length; i++) {
                    int[] temp = AlgEuclidesEstes(P[i], n[i]); // trobar els inversos
                    Q[i] = temp[0];
                }
                for (int i = 0; i < P.length; i++) {
                    c += b[i] * P[i] * Q[i];
                }
                while (c < 0) {
                    c += N;
                    // canviar el resultat a un numero positiu
                }


                return new int[]{c, N};
            } else { // si no es pot solucionar pel TXR
                boolean Solucionable = true;
                for (int k = 0; k < n.length - 1; k++) {
                    if ((b[k + 1] - b[k]) % ((MCDEuclides(n[k], n[k + 1]))) != 0) {
                        Solucionable = false;// no existeix la seva solucio
                    }
                }
                if (Solucionable) {// solucionar dos per dos equacions
                    int [] solucio = new int [2];
                    for (int k = 0; k < n.length - 1; k++) {
                        // obtenim les dues equacions
                        int[] temp = {b[k], b[k + 1]};
                        int[] ntemp = {n[k], n[k + 1]};
                        solucio = Sistema2Cong(temp, ntemp); // guardem la solucio de les 2 equaciones en solucio[]
                        b[k + 1] = solucio[0];
                        n[k + 1] = solucio[1];
                    }
                    return solucio;

                }
            }
            return null;
        }
        public static boolean TeSolpelTXR(int[] n){ // segons el TXR
            for (int i =0; i<n.length-1; i++){
                if (MCDEuclides(n[i], n[i+1])!=1){
                    return false;
                }
            }
            return true;
        }

        /*
         * Donau la solució (totes) del sistema d'equacions
         *
         *  { a[0]·x ≡ b[0] (mod n[0])
         *  { a[1]·x ≡ b[1] (mod n[1])
         *  { a[2]·x ≡ b[2] (mod n[2])
         *  { ...
         *
         * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
         * podeu suposar que els tres arrays tenen la mateixa longitud.
         *
         * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */
        static int [] exercici2b(int[] a, int[] b, int[] n) {
            // simplificar les equacions
            boolean Solucionable = true;
            int [] solucio = new int[2];

            for (int i = 0; i < n.length; i++) {
                int temp = MCDEuclides(a[i], b[i]);
                if (temp != 0) {
                    a[i] /= temp;
                    b[i] /= temp;
                }
            }

            //trobar els inversos
            for (int i = 0; i < a.length; ++i) {
                a[i] = Invers(a[i], n[i]);
                b[i]*= a[i];
            }

            if (TeSolpelTXR(n)) {
                return exercici2a(b, n); // es pot resoldre amb el TXR
            }


            for (int k = 0; k<n.length-1; k++) {
                if ((b[k + 1] - b[k]) % ((MCDEuclides(n[k], n[k+1]))) != 0) {
                    Solucionable = false;// no existeix la seva solucio
                }
            }
            if (Solucionable){// solucionar dos per dos equacions

                for (int k = 0; k<n.length-1; k++) {
                    // obtenim les dues equacions
                    int[] temp = {b[k], b[k + 1]};
                    int[] ntemp = {n[k], n[k + 1]};
                    solucio = Sistema2Cong(temp, ntemp); // guardem la solucio de les 2 equaciones en solucio[]
                    b[k + 1] = solucio[0];
                    n[k + 1] = solucio[1];
                }
                return solucio;
            }

            return null;
        }

        // funció auxiliar per soluciona un sistema de 2 congreuncies
        static int [] Sistema2Cong(int b [], int n[]){
            int res[] = AlgEuclidesEstes(n[0], n[1]); // obtener els valors de x i y que solucion l'equacio diofantina: n[0]*x + n[1]*y = b[1]-b[0]
            int  ValorX = res[0];
            int  ValorY = res[1];
            int factor =(b[1]-b[0])/MCDEuclides(n[0],n[1]);

            int N = n[0]*n[1];
            ValorX = (ValorX * n[0]*factor)+b[0];
            while (ValorX < 0){
                ValorX +=N;
            }
            ValorX %=N;
            int MCM = N/MCDEuclides(n[0], n[1]); // trobar el mínim comú multiple dels moduls

            return new int [] {ValorX, MCM};
        }

        /*
         * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
         * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
         *
         * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
         *
         * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
         * (el que coneixeu com el mètode manual d'anar provant).
         */
        public static ArrayList<Integer> exercici3a(int n) {
            ArrayList<Integer> DescomposicioFactorial = new ArrayList<>();
            for (int i = 2; i <= n; i++) {
                while (EsPrimer(i)) {
                    while (n % i == 0) {
                        DescomposicioFactorial.add(i);
                        n = n / i;
                    }
                    i++;
                }

            }

            return DescomposicioFactorial;
        }

        public static  boolean EsPrimer(int x){
            int contador=0;
            for (int i = 1; i<=x; i++){
                if (x%i == 0){
                    contador++;
                }
            }
            return contador == 2; // nombre dels seus divisors, donat que un nombre primer és només divisible per ell mateix i 1
        }

        /*
         * Retornau el nombre d'elements invertibles a Z mòdul n³.
         *
         * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
         * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
         *
         * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
         */

        // Funció auxiliar per comptar nombre de repeticios d'un elemet en un ArrayList
        public static int contarRepeticiones(ArrayList<Integer> numeros, int elemento) {
            int contador = 0;
            for (int num : numeros) {
                if (num == elemento) {
                    contador++;
                }
            }
            return contador;
        }

        /* S'han emprat les seguents propietats:
        * Si x és primer -> phi(x)=x-1
        * phi(x)=phi(n1)*phi(n2)... donat que x = n1*n2...
        * I per tant per no calcular la tercera potencia del numero n, calculem les dels seus factors
         */
        public static int exercici3b(int n) {

            ArrayList<Integer> descomposicioFactorial = exercici3a(n);
            ArrayList<Integer> FactorsNoRepetits = new ArrayList<>();
            ArrayList<Integer> Potencies = new ArrayList<>();
            HashSet<Integer> conjunt = new HashSet<>(descomposicioFactorial);

            for (int element : conjunt) {
                FactorsNoRepetits.add(element);
                Potencies.add(contarRepeticiones(descomposicioFactorial, element));
            }

            for (int i = 0; i<Potencies.size(); i++){
                Potencies.set(i, Potencies.get(i)*3);
            }


            int resultat = 1;
            for (int i = 0; i<FactorsNoRepetits.size();i++){
                resultat *= Math.pow(FactorsNoRepetits.get(i),Potencies.get(i) ) - Math.pow(FactorsNoRepetits.get(i),Potencies.get(i)-1 );
            }



            return resultat;
        }


        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
            assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
            assertThat(exercici1(2, 3, 6) == null);

            assertThat(
                    exercici2a(
                            new int[] { 1, 0 },
                            new int[] { 2, 4 }
                    )
                            == null
            );

            assertThat(
                    Arrays.equals(
                            exercici2a(
                                    new int[] { 3, -1, 2 },
                                    new int[] { 5,  8, 9 }
                            ),
                            new int[] { 263, 360 }
                    )
            );

            assertThat(
                    exercici2b(
                            new int[] { 1, 1 },
                            new int[] { 1, 0 },
                            new int[] { 2, 4 }
                    )
                            == null
            );

            assertThat(
                    Arrays.equals(
                            exercici2b(
                                    new int[] { 2,  -1, 5 },
                                    new int[] { 6,   1, 1 },
                                    new int[] { 10,  8, 9 }
                            ),
                            new int[] { 263, 360 }
                    )
            );

            assertThat(exercici3a(10).equals(List.of(2, 5)));
            assertThat(exercici3a(1291).equals(List.of(1291)));
            assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

            assertThat(exercici3b(10) == 400);

            // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
            assertThat(exercici3b(1292) == 961_496_064);

            // Aquest exemple té el resultat fora de rang
            //assertThat(exercici3b(1291) == 2_150_018_490);
        }
    }

    /*
     * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
     * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
     * compte, però és molt recomanable).
     *
     * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
     */
    public static void main(String[] args) {
        Tema1.tests();
        Tema2.tests();
        Tema3.tests();
        Tema4.tests();
    }

    /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
    static void assertThat(boolean b) {
        if (!b)
            throw new AssertionError();
    }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
