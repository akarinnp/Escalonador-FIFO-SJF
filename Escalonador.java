import java.util.*;

class Processo {
    int id;
    int tempoChegada;
    int tempoExecucao;

    public Processo(int id, int tempoChegada, int tempoExecucao) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
    }
}

public class Escalonador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("--------------------Escalonador FIFO/SJF-------------------");
            System.out.print("Informe a quantidade de processos:");
            int numProcessos = scanner.nextInt();
            System.out.println("-----------------------------------------------------------");

            List<Processo> processos = new ArrayList<>();

            for (int i = 1; i <= numProcessos; i++) {
                System.out.println("Informe o tempo de chegada e tempo de execução do processo " + i + ":");
                System.out.print("Chegada: ");
                int chegada = scanner.nextInt();
                System.out.print("Execução: ");
                int execucao = scanner.nextInt();
                processos.add(new Processo(i, chegada, execucao));
                System.out.println("-----------------------------------------------------------");
            }

            // Ordenar os processos por tempo de chegada para os algoritmos FIFO e SJF
            Collections.sort(processos, Comparator.comparingInt(p -> p.tempoChegada));
            
            //CALCULO FIFO
            int tempoTotalEsperaFIFO = 0;
            int tempoTotalProcessamentoFIFO = 0;
            int tempoAtualFIFO = 0;
            
            List<Processo> validProcessosFIFO = new ArrayList<>();
            
            for (Processo p : processos) {
                tempoTotalEsperaFIFO += tempoAtualFIFO - p.tempoChegada;
                tempoTotalProcessamentoFIFO += tempoAtualFIFO + p.tempoExecucao - p.tempoChegada;
                tempoAtualFIFO += p.tempoExecucao;
                validProcessosFIFO.add(p);
            }

            double tmeFIFO = (double) tempoTotalEsperaFIFO / numProcessos;
            double tmpFIFO = (double) tempoTotalProcessamentoFIFO / numProcessos;
            
            System.out.println("");
            //GRAFICO FIFO
            System.out.println("------------------- FIFO - Gráfico ---------------------");

            int maxTimeFIFO = tempoAtualFIFO;
            if (maxTimeFIFO % 10 != 0) {
                maxTimeFIFO = ((maxTimeFIFO / 10) + 1) * 10;
            }

            int tempoTotalFIFO = 0;

            for (Processo p : validProcessosFIFO) {
                System.out.printf("P%d ", p.id);
                for (int j = 0; j < maxTimeFIFO; j++) {
                    if (j >= tempoTotalFIFO && j < tempoTotalFIFO + p.tempoExecucao && j >= p.tempoChegada) {
                        System.out.print("■");
                    } else {
                        System.out.print(" ");
                    }
                }
                tempoTotalFIFO += p.tempoExecucao;
                System.out.println(" " + tempoTotalFIFO);
            }
            //TERMINO GRAFICO FIFO
            
            // Calcular TME e TMP para FIFO
            System.out.println("TMP (FIFO): " + tmpFIFO + "\tTME (FIFO): " + tmeFIFO );
            System.out.println("");

            //CALCULO SJF
            int tempoTotalEsperaSJF = 0;
            int tempoTotalProcessamentoSJF = 0;
            int tempoAtualSJF = 0;
            
            List<Processo> validProcessosSJF = new ArrayList<>();

            while (!processos.isEmpty()) {
                Processo menorTempoExecucao = processos.get(0);
            
                for (Processo p : processos) {
                    if (p.tempoChegada <= tempoAtualSJF && p.tempoExecucao < menorTempoExecucao.tempoExecucao) {
                        menorTempoExecucao = p;
                    }
                }
            
                if (menorTempoExecucao != null) {
                    tempoTotalEsperaSJF += tempoAtualSJF - menorTempoExecucao.tempoChegada;
                    tempoAtualSJF += menorTempoExecucao.tempoExecucao;
                    tempoTotalProcessamentoSJF += tempoAtualSJF - menorTempoExecucao.tempoChegada;
                    processos.remove(menorTempoExecucao);
                    validProcessosSJF.add(menorTempoExecucao);
                } else {
                    tempoAtualSJF++;
                }
            }
            
            double tmeSJF = (double) tempoTotalEsperaSJF / numProcessos;
            double tmpSJF = (double) tempoTotalProcessamentoSJF / numProcessos;

            //GRAFICO SJF
            System.out.println("------------------- SJF - Gráfico ---------------------");

            int maxTimeSJF = tempoAtualSJF;
            if (maxTimeSJF % 10!= 0) {
                maxTimeSJF = ((maxTimeSJF / 10) + 1) * 10;
            }

            int tempoTotalSJF = 0;

            for (Processo p : validProcessosSJF) {
                System.out.printf("P%d ", p.id);
                for (int j = 0; j < maxTimeSJF; j++) {
                    if (j >= tempoTotalSJF && j < tempoTotalSJF + p.tempoExecucao && j >= p.tempoChegada) {
                        System.out.print("■");
                    } else {
                        System.out.print(" ");
                    }
                }
                tempoTotalSJF += p.tempoExecucao;
                System.out.println(" " + tempoTotalSJF);
            }
            
            // Calcular TME e TMP para SJF
            System.out.println("TMP (SJF): " + tmpSJF + "\t       TME (SJF): " + tmeSJF);
            System.out.println("");

            // Perguntar ao usuário se deseja calcular novamente
            System.out.println("Deseja calcular novamente? (S/N)");
            String resposta = scanner.next();
            if (!resposta.equalsIgnoreCase("S")) {
                continuar = false;
            }
        }
        
        scanner.close();
    }
}
