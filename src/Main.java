import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    // Number of individuals in each generation
    private static final int POPULATION_SIZE = 100;

    // Target string to be generated
    private static final String TARGET = "Generative AI";

    // Function to generate random numbers in given range
    private static int randomNum(int start, int end) {
        Random rand = new Random();
        return rand.nextInt(end - start + 1) + start;
    }

    // Create random genes for mutation
    private static char mutation() {
        // Generate a random ASCII character from space (32) to tilde (126)
        return (char) randomNum(32, 126);
    }

    // Create chromosome or string of genes
    private static String createGnome() {
        int len = TARGET.length();
        StringBuilder gnome = new StringBuilder();
        for (int i = 0; i < len; i++) {
            gnome.append(mutation());
        }
        return gnome.toString();
    }

    // Class representing individual in population
    private static class Individual implements Comparable<Individual> {
        String chromosome;
        int fitness;

        Individual(String chromosome) {
            this.chromosome = chromosome;
            fitness = calFitness();
        }

        // Perform crossover (mating) and produce new offspring
        Individual crossover(Individual par2) {
            StringBuilder childChromosome = new StringBuilder();

            int len = chromosome.length();
            for (int i = 0; i < len; i++) {
                // random probability
                float p = randomNum(0, 100) / 100f;

                // if prob is less than 0.45, insert gene from parent 1
                if (p < 0.45)
                    childChromosome.append(chromosome.charAt(i));

                    // if prob is between 0.45 and 0.90, insert gene from parent 2
                else if (p < 0.90)
                    childChromosome.append(par2.chromosome.charAt(i));

                    // otherwise insert random gene (mutation), for maintaining diversity
                else
                    childChromosome.append(mutation());
            }

            return new Individual(childChromosome.toString());
        }

        // Calculate fitness score, it is the number of characters in string which differ from target string
        private int calFitness() {
            int len = TARGET.length();
            int fitness = 0;
            for (int i = 0; i < len; i++) {
                if (chromosome.charAt(i) != TARGET.charAt(i))
                    fitness++;
            }
            return fitness;
        }

        @Override
        public int compareTo(Individual o) {
            return Integer.compare(this.fitness, o.fitness);
        }
    }

    // Selection function to choose individuals for the next generation
    private static List<Individual> selection(List<Individual> population) {
        Collections.sort(population); // Sort population by fitness

        // Elitism: Select top 10% the fittest individuals to carry over unchanged to the next generation
        int elitismSize = (int) (0.1 * POPULATION_SIZE);
        List<Individual> selected = new ArrayList<>(population.subList(0, elitismSize));

        // Random selection of individuals for mating from the remaining population
        for (int i = elitismSize; i < POPULATION_SIZE; i++) {
            selected.add(population.get(randomNum(0, elitismSize - 1)));
        }

        return selected;
    }

    // Driver code
    public static void main(String[] args) {

        int totalGenerationsAll = 0; // To calculate the total number of generations across all simulations

        for (int simulation = 1; simulation <= 2; simulation++) {
            System.out.println("Simulation " + simulation + " Start:");
            long startTime = System.currentTimeMillis();

            // current generation
            int generation = 0;

            List<Individual> population = new ArrayList<>();

            // create initial population
            for (int i = 0; i < POPULATION_SIZE; i++) {
                String gnome = createGnome();
                population.add(new Individual(gnome));
            }

            while (true) {
                // Perform selection
                population = selection(population);

                // if the individual having the lowest fitness score i.e. 0 then we have reached the target
                if (population.getFirst().fitness <= 0) {
                    break;
                }

                // Generate new offsprings for new generation

                population = getIndividuals(population);

                // Output generation information
                System.out.print("Generation: " + generation + "\t");
                System.out.print("String: " + population.getFirst().chromosome + "\t");
                System.out.println("Fitness: " + population.getFirst().fitness);

                generation++;
            }

            // Output final generation information
            System.out.print("Generation: " + generation + "\t");
            System.out.print("String: " + population.getFirst().chromosome + "\t");
            System.out.println("Fitness: " + population.getFirst().fitness);

            totalGenerationsAll += generation;

            long endTime = System.currentTimeMillis();
            System.out.print("Simulation " + simulation + " End. Time spent: " + (endTime - startTime) + " ms\n");

            // Calculate and output average generation count for each simulation
            System.out.println("Generation Count for Simulation " + simulation + ": " + (generation));
            System.out.println();
        }

        // Calculate and output average generation count across all simulations
        System.out.println("Average Generation Count Across All Simulations: " + (totalGenerationsAll / 2.0));
        System.out.println("Population Size: " + POPULATION_SIZE);
    }

    private static List<Individual> getIndividuals(List<Individual> population) {
        List<Individual> newGeneration = new ArrayList<>();

        // From selected population, Individuals will mate to produce offspring
        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            Individual parent1 = population.get(i);
            Individual parent2 = population.get(i + 1);
            Individual offspring = parent1.crossover(parent2);
            newGeneration.add(offspring);
        }
        return newGeneration;
    }
}
