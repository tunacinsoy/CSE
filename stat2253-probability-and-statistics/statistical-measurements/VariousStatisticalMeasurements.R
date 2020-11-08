# THIS SOURCE CODE RETRIEVES DATA FROM EXCEL AND CALCULATES RELEVANT STATISTICAL VALUES
# Author: Tuna Cinsoy / 20.03.2020

library(readr)
HW1_Data1 <- read_csv("D:/Database/HW1_Data1.csv")

men <- subset(HW1_Data1, GENDER == "0")
women <- subset(HW1_Data1, GENDER == "1")

# ANSWER OF A
mean_of_systolic_of_men <- mean(men$SYSBP)
mean_of_systolic_of_women <- mean(women$SYSBP)

mean_of_diastolic_of_men <- mean(men$DIASBP)
mean_of_diastolic_of_women <- mean(women$DIASBP)

# ANSWER OF B
variance_of_systolic_of_men <- var(men$SYSBP)
variance_of_systolic_of_women <- var(women$SYSBP)

variance_of_diastolic_of_men <- var(men$DIASBP)
variance_of_diastolic_of_women <- var(women$DIASBP)

#ANSWER OF C
standard_deviation_of_systolic_of_men <- sd(men$SYSBP)
standard_deviation_of_systolic_of_women <- sd(women$SYSBP)

standard_deviation_of_diastolic_of_men <- sd(men$DIASBP)
standard_deviation_of_diastolic_of_women <- sd(men$DIASBP)

#ANSWER OF D
quantile_of_systolic_of_men <- quantile(men$SYSBP)
quantile_of_systolic_of_women <- quantile(women$SYSBP)

quantile_of_diastolic_of_men <- quantile(men$DIASBP)
quantile_of_diastolic_of_women <- quantile(women$DIASBP)

#ANSWER OF E
max_value_of_systolic_of_men <- max(men$SYSBP)
max_value_of_systolic_of_women <- max(women$SYSBP)

max_value_of_diastolic_of_men <- max(men$DIASBP)
max_value_of_diastolic_of_women <- max(women$DIASBP)

min_value_of_systolic_of_men <- min(men$SYSBP)
min_value_of_systolic_of_women <- min(women$SYSBP)

min_value_of_diastolic_of_men <- min(men$DIASBP)
min_value_of_diastolic_of_women <- min(women$DIASBP)

#ANSWER OF F
range_of_systolic_of_men <- (max_value_of_systolic_of_men - min_value_of_systolic_of_men)
range_of_systolic_of_women <- (max_value_of_systolic_of_women - min_value_of_systolic_of_women)

range_of_diastolic_of_men <- (max_value_of_diastolic_of_men - min_value_of_diastolic_of_men)
range_of_diastolic_of_women <- (max_value_of_diastolic_of_women - min_value_of_diastolic_of_women)

# ANSWER OF G
range_over_standard_deviation_of_systolic_of_men <- (range_of_systolic_of_men / standard_deviation_of_systolic_of_men)
range_over_standard_deviation_of_systolic_of_women <- (range_of_systolic_of_women / standard_deviation_of_systolic_of_women)

range_over_standard_deviation_of_diastolic_of_men <- (range_of_diastolic_of_men / standard_deviation_of_diastolic_of_men)
range_over_standard_deviation_of_diastolic_of_women <- (range_of_diastolic_of_women / standard_deviation_of_diastolic_of_women)

#ANSWER OF H
median_value_of_systolic_of_men <- median(men$SYSBP)
median_value_of_systolic_of_women <- median(women$SYSBP)

median_value_of_diastolic_of_men <- median(men$DIASBP)
median_value_of_diastolic_of_women <- median(women$DIASBP)

#ANSWER OF I
iqr_of_systolic_of_men <- IQR(men$SYSBP)
iqr_of_systolic_of_women <- IQR(women$SYSBP)

iqr_of_diastolic_of_men <- IQR(men$DIASBP)
iqr_of_diastolic_of_women <- IQR(women$DIASBP)

#ANSWER OF J
summary_of_systolic_of_men <- summary(men$SYSBP)
summary_of_systolic_of_women <- summary(women$SYSBP)

summary_of_diastolic_of_men <- summary(men$DIASBP)
summary_of_diastolic_of_women <- summary(women$DIASBP)

#------------------GRAPH PART------------------------------------

#ANSWER OF K
boxplot(men$SYSBP, main = "Boxplot SYS Men" ,xlab = "Count of Men", ylab = "Systolic Data of Men")
boxplot(women$SYSBP, main = "Boxplot SYS Women" ,xlab = "Count of Women", ylab = "Systolic Data of Women")

boxplot(men$DIASBP, main = "Boxplot DIAS Men" ,xlab = "Count of Men", ylab = "Diastolic Data of Men")
boxplot(women$DIASBP, main = "Boxplot DIAS Women" ,xlab = "Count of Women", ylab = "Diastolic data of Women")

# ANSWER OF L
stem(men$SYSBP)
stem(women$SYSBP)

stem(men$DIASBP)
stem(women$DIASBP)

# ANSWER OF M
hist(men$SYSBP, breaks = 5)
hist(women$SYSBP, breaks = 20)

hist(men$DIASBP, breaks = 5)
hist(women$DIASBP, breaks = 20)

# ANSWER OF N
dotchart(men$SYSBP)
dotchart(women$SYSBP)

dotchart(men$DIASBP)
dotchart(women$DIASBP)

#ANSWER OF Q
dotchart(men$SYSBP,women$SYSBP, xlab = "systolic of men", ylab = "systolic of women")
dotchart(men$DIASBP,women$DIASBP, xlab = "diastolic of men", ylab = "diastolic of women")

#ANSWER OF R
library(lattice)
histogram(men$SYSBP, xlab = "Systolic of Men", ylab = "% Frequency")
histogram(women$SYSBP, xlab = "Systolic of Women", ylab = "% Frequency")

histogram(men$DIASBP, xlab = "Systolic of Men", ylab = "% Frequency")
histogram(women$DIASBP, xlab = "Diastolic of Women", ylab = "% Frequency")

#ANSWER OF S
z_score_of_max_of_systolic_of_men <- (max_value_of_systolic_of_men - mean_of_systolic_of_men) / standard_deviation_of_systolic_of_men
z_score_of_max_of_systolic_of_women <- (max_value_of_systolic_of_women - mean_of_systolic_of_women) / standard_deviation_of_systolic_of_women

z_score_of_max_of_diastolic_of_men <- (max_value_of_diastolic_of_men - mean_of_diastolic_of_men) / standard_deviation_of_diastolic_of_men
z_score_of_max_of_diastolic_of_women <- (max_value_of_diastolic_of_women - mean_of_diastolic_of_women) / standard_deviation_of_diastolic_of_women

z_score_of_min_of_systolic_of_men <- (min_value_of_systolic_of_men - mean_of_systolic_of_men) / standard_deviation_of_systolic_of_men
z_score_of_min_of_systolic_of_women <- (min_value_of_systolic_of_women - mean_of_systolic_of_women) / standard_deviation_of_systolic_of_women

z_score_of_min_of_diastolic_of_men <- (min_value_of_diastolic_of_men - mean_of_diastolic_of_men) / standard_deviation_of_diastolic_of_men
z_score_of_min_of_diastolic_of_women <- (min_value_of_diastolic_of_women - mean_of_diastolic_of_women) / standard_deviation_of_diastolic_of_women















