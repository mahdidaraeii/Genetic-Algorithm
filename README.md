# Java Genetic Algorithm for String Generation

This Java program implements a genetic algorithm to generate a target string.

## Overview

- **Target String**: The program aims to generate the target string "Generative AI".
- **Population Size**: 100 individuals per generation.
- **Elitism**: Top 10% fittest individuals from each generation are carried over unchanged to the next.
- **Mutation Rate**: Randomly introduces mutations to maintain diversity.
- **Simulation**: The program runs 2 simulations to demonstrate variability in performance.

## Algorithm Steps

1. **Initialization**: Create an initial population of random strings.
2. **Evaluation**: Calculate the fitness of each individual based on its similarity to the target string.
3. **Selection**: Choose individuals from the population for the next generation based on their fitness.
4. **Crossover**: Generate new individuals (offspring) through crossover/mating of selected individuals.
5. **Mutation**: Introduce random mutations in offspring to maintain diversity.
6. **Termination**: Stop when an individual with zero fitness (perfect match to target string) is found.

## How to Run

1. Compile and run the `Main.java` file in a Java IDE or from the command line.
2. Monitor the output to observe the progress of each simulation, including generation count, generated strings, and fitness.
3. Adjust parameters in the code (e.g., population size, mutation rate) to explore different configurations.

## Note

- The program demonstrates a basic genetic algorithm approach and can be extended or modified for various applications.
- Running the program multiple times may produce different results due to the stochastic nature of genetic algorithms.
