#####################################################
# for Generating Quick Summary of the Model
#####################################################

setwd("G:/GGS/RFiles")
library(igraph)

args     <- commandArgs(TRUE)
fileName <- args[1]
index    <- args[2]

Inputpath  <- fileName

myData  <- read.table(Inputpath, sep = "", header = F)
myGraph <- graph.data.frame(myData,directed = FALSE)


cc      <- average.path.length(myGraph)
myGraph <- delete.edges(myGraph, index)
comp    <- count_components(myGraph)
if(comp > 1) cc <- 0.0

cat(cc)

