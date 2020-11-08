# Probability and Statistics HW2
# This program aims to find out the differences between empirical and theoretical values of mean and standard deviation.
# Author: Tuna Cinsoy / 150117062
# Date: 22.05.2020


load(file = "Homework_2_50samples.rdata")

# SOLUTION OF QUESTION A
sample_mean_column = ex0717$`Sample mean`
hist(sample_mean_column, breaks = 5)
print("Distribution is mound-shaped.")

# SOLUTION OF QUESTION B
empirical_mean_of_sample_mean_column <- mean(ex0717$`Sample mean`)
empirical_standard_deviation_of_sample_mean_column <- sd(ex0717$`Sample mean`)

# SOLUTION OF QUESTION C
theoretical_mean <- 4.4

theoretical_standard_deviation <- 2.15 / sqrt(10)

difference_between_means <- 0

if (empirical_mean_of_sample_mean_column < theoretical_mean) {
  print("Empirical mean is less than theoretical mean.")
  difference_between_means <- theoretical_mean - empirical_mean_of_sample_mean_column
  
} else if (empirical_mean_of_sample_mean_column > theoretical_mean) {
  print("Empirical mean is greater than theoretical mean.")
  difference_between_means <- empirical_mean_of_sample_mean_column - theoretical_mean
  
} else {
  print("Empirical mean is equal to theoretical mean.")
  
}

sprintf("The difference between empirical mean and theoretical mean value is %f ", difference_between_means)

if (empirical_standard_deviation_of_sample_mean_column < theoretical_standard_deviation) {
  print("Empirical standard deviation is less than theoretical standard deviation.")
  difference_between_standard_deviations <- theoretical_standard_deviation - empirical_standard_deviation_of_sample_mean_column
  
} else if (empirical_standard_deviation_of_sample_mean_column > theoretical_standard_deviation) {
  print("Empirical standard deviation is greater than theoretical standard deviation.")
  difference_between_standard_deviations <- empirical_standard_deviation_of_sample_mean_column - theoretical_standard_deviation
  
} else {
  print("Empirical standard deviation is equal to theoretical standard deviation.")
  difference_between_standard_deviations <- 0
}

sprintf("The difference between empirical standard deviation and theoretical standard deviation value is %f ", difference_between_standard_deviations)