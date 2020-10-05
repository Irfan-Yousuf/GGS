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
  
  
  assort  <- assortativity.degree(myGraph)
  assort  <- assort + 1 # to range [0-2]
  myGraph <- delete.edges(myGraph, index)
  comp    <- count_components(myGraph)
  if(comp > 1) assort <- 3.0
  
  cat(assort)



