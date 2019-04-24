package astar;

import java.util.*;

class AEstrella {
    private static final int costeMovimientoHyV = 2; // Horizontal - Vertical Cost
    // private static final int DEFAULT_DIAGONAL_COST = 14;

    //private int diagonalCost;
    private Casilla[][] searchArea;
    private PriorityQueue<Casilla> listaAbierta;
    private List<Casilla> listaCerrada;
    private ArrayList<Casilla> camino = new ArrayList<>();
    private ArrayList<Casilla> keys;
    private Casilla casillaInicial, casillaActual;
    private Casilla casillaFinal;

    public ArrayList<Casilla> getCamino() {
        return camino;
    }

    private AEstrella(int rows, int cols, Casilla casillaInicial, Casilla casillaFinal, ArrayList<Casilla> keys, int hvCost) {

        setCasillaInicial(casillaInicial);
        setCasillaFinal(casillaFinal);
        this.searchArea = new Casilla[rows][cols];
        this.listaAbierta = new PriorityQueue<>(Comparator.comparingInt(Casilla::getCosteTotal));
        this.keys = keys;


        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                this.searchArea[i][j] = new Casilla(i, j);
                searchArea[i][j].calculateHeuristic(getCasillaFinal());
            }
        }


        this.listaCerrada = new ArrayList<>();

    }

    AEstrella(int rows, int cols, Casilla casillaInicial, Casilla casillaFinal, ArrayList<Casilla> keys) {
        this(rows, cols, casillaInicial, casillaFinal, keys, costeMovimientoHyV);
    }


    void empezarAlgoritmo() {

        System.out.println("\nAl is in: " + casillaInicial.toString() + '\n');
        System.out.println("There's keys at positions:\n");
        for (int i = 0; i < keys.size(); i++) {
            System.out.println(keys.get(i).toString());
        }
        System.out.println("\nSimulation Begins!\n---------------------------------------------");

        //while keys != 0 destino -> llave mas cercana
        while (!keys.isEmpty()) {
            calcularDistanciasLlaves(keys, casillaActual);
            keys.sort(Comparator.comparingInt(Casilla::getCostToKey));
            System.out.println("\n\nTo key [" + keys.get(0).toString() + "]\n");
            procesarCasillas(keys.get(0));
            keys.remove(0);
        }
        //llaves==0 destino -> E (casillafinal)
        System.out.println("\nTo exit\n" + casillaFinal.toString());
        procesarCasillas(casillaFinal);
    }

    private void calcularDistanciasLlaves(ArrayList<Casilla> keys, Casilla casillaActual) {
        if (casillaActual == null) {
            casillaActual = casillaInicial;
        }
        if (!keys.isEmpty()) {
            for (Casilla key : keys) {
                key.setCostToKey(casillaActual.calculateHeuristic(key));
            }
        }
    }


    private void calcularCostes(Casilla casillaActual, Casilla casillaIterativa, Casilla casillaDestino) {
        if (!listaCerrada.contains(casillaIterativa) && casillaIterativa != null) {

            int costeFinalIterativa = casillaIterativa.getCosteHeuristica(casillaDestino) + costeMovimientoHyV;
            boolean estaEnListaAbierta = listaAbierta.contains(casillaIterativa);

            if (!estaEnListaAbierta || costeFinalIterativa < casillaIterativa.getCosteTotal()) {

                casillaIterativa.setCosteTotal(costeFinalIterativa);
                casillaIterativa.setParent(casillaActual);


                if (!estaEnListaAbierta) {

                    listaAbierta.add(casillaIterativa);
                }
            }

        }
    }


    void setParedes(int i, int j) {
        searchArea[i][j] = null;
    }


    void procesarCasillas(Casilla casillaDestino) {
        if (casillaActual == null) {
            casillaActual = casillaInicial;
        }
        listaAbierta.add(casillaActual);
        Casilla casillaIterativa;

        do {
            casillaActual = listaAbierta.poll();
            if (casillaActual != null) {
                listaCerrada.add(casillaActual);

                if (casillaActual.equals(casillaDestino)) {
                    addToPath(casillaActual);
                    if (casillaActual.equals(casillaFinal)) {
                        casillaFinal = casillaActual;
                    }
                    break;
                }


                if (casillaActual.getRow() - 1 >= 0) {
                    casillaIterativa = searchArea[casillaActual.getRow() - 1][casillaActual.getCol()];
                    calcularCostes(casillaActual, casillaIterativa, casillaDestino);
                }
                if (casillaActual.getCol() - 1 >= 0) {
                    casillaIterativa = searchArea[casillaActual.getRow()][casillaActual.getCol() - 1];
                    calcularCostes(casillaActual, casillaIterativa, casillaDestino);
                }
                if (casillaActual.getCol() + 1 < searchArea[0].length) {
                    casillaIterativa = searchArea[casillaActual.getRow()][casillaActual.getCol() + 1];
                    calcularCostes(casillaActual, casillaIterativa, casillaDestino);
                }
                if (casillaActual.getRow() + 1 < searchArea[0].length) {
                    casillaIterativa = searchArea[casillaActual.getRow() + 1][casillaActual.getCol()];
                    calcularCostes(casillaActual, casillaIterativa, casillaDestino);
                }


            }
        } while (casillaActual != null);


    }

    void addToPath(Casilla destino) {
        Casilla casillaActual = destino;
        while (casillaActual != null) {
            camino.add(casillaActual);
            casillaActual = casillaActual.getParent();
        }
    }


    private void setCasillaInicial(Casilla casillaInicial) {
        this.casillaInicial = casillaInicial;
    }

    private Casilla getCasillaFinal() {
        return casillaFinal;
    }

    private void setCasillaFinal(Casilla casillaFinal) {
        this.casillaFinal = casillaFinal;
    }


}

