#####################################################
# for Generating Quick Summary of the Model
#####################################################

setwd("G:/GGS/RFiles")
library(igraph)
library(data.table)

args        <- commandArgs(TRUE)
fileName    <- args[1]

Inputpath   <- fileName

myData  <- read.table(Inputpath, sep = "", header = F)
myGraph <- graph.data.frame(myData,directed = FALSE)
fullS   <- average.path.length(myGraph)

tEdges  <- ecount(myGraph)

for(i in 1:tEdges){
  myG           <- copy(myGraph)
  myG           <- delete.edges(myG, i)
  cc            <- average.path.length(myG)
  cc            <- fullS - cc
  comp          <- count_components(myG)
  cat(cc, " ", comp,  "\n")
}

