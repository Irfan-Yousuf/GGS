#####################################################
# for Generating Quick Summary of the Model
#####################################################

setwd("G:/GGS/RFiles")
library(igraph)
library(data.table)

args       <- commandArgs(TRUE)
fileName   <- args[1]
Inputpath  <- fileName

  myData  <- read.table(Inputpath, sep = "", header = F)
  myGraph <- graph.data.frame(myData,directed = FALSE)
  fullS   <- assortativity.degree(myGraph)
  fullS   <- fullS + 1 # to range [0-2]
  tEdges  <- ecount(myGraph)
  
  for(i in 1:tEdges){
    myG           <- copy(myGraph)
    myG           <- delete.edges(myG, i)
    assort        <- assortativity.degree(myG)
    assort        <- fullS - (assort + 1) # to range [0-2]
    comp          <- count_components(myG)
    cat(assort, " ", comp,  "\n")
  }




