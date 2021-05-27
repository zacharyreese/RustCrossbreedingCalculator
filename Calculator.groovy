import Gene
import com.github.javaparser.ast.expr.DoubleLiteralExpr

import java.io.File
import java.util.ArrayList
class Calculator {

    static def desiredGene = ['G', 'Y', 'Y', 'Y', 'G']

    static void main(String[] args) {
        def genePool = []

        //Import txt file
        def input = new File('C:\\Users\\Zac\\Documents\\Coding\\RustCrossbreeding\\src\\input.txt')
        //Loop through each line of input to get gene pools
        input.eachLine { line ->
            def geneList = []
            //Loop through each gene pool and create a list of genes
            line.each { geneText ->
                def gene = geneText.toUpperCase()
                geneList += gene
            }
            //Generate a list of gene arrays
            genePool.add(geneList)
        }

        //Search all the genes for a match of desiredGene
        searchAllGenes(genePool)

    }

    //Loop through every gene to find desired gene
    static def searchAllGenes(def genePool) {
        HashSet geneSet = new HashSet()
        //Keep an array of 5 genes that will calculate the new gene
        def genesToBeCalculated = []

        for (int a = 0; a < genePool.size(); a++) {
            genesToBeCalculated[0] = genePool[a]

            for (int b = 0; b < genePool.size(); b++) {
                genesToBeCalculated[1] = genePool[b]

                for (int c = 0; c < genePool.size(); c++) {
                    genesToBeCalculated[2] = genePool[c]

                    for (int d = 0; d < genePool.size(); d++) {
                        genesToBeCalculated[3] = genePool[d]
                        def calculatedGene = calculateDefinedGenePool(genesToBeCalculated)
                        geneSet.add(calculatedGene)
                        if (isDesiredGene(calculatedGene)) {
                            println(genesToBeCalculated)
                            println(calculatedGene)
                            return
                        }
                        //println(geneSet)

                    }
                }
            }
        }

    }

    static def isDesiredGene(def genes) {
        HashMap<String, Double> desiredGeneCount = new HashMap<String, Double>()
        HashMap<String, Double> inputGeneCount = new HashMap<String, Double>()
        //Get HashMap for desired gene count
        desiredGene.each { gene ->
            gene.each { letter ->
                Gene newGene = new Gene(letter)
                //Make a hashmap with total weight of each gene ex: [G:1.5, Y:1.0] for [G, G, G, Y, Y]
                desiredGeneCount.put(newGene.type, (newGene.weight + (desiredGeneCount.get(newGene.type) ?: 0)))
            }
        }
        genes.each { gene ->
            gene.each { letter ->
                Gene newGene = new Gene(letter)
                inputGeneCount.put(newGene.type, (newGene.weight + (inputGeneCount.get(newGene.type) ?: 0)))
            }
        }
        def flag = true
        desiredGeneCount.each { gene ->
            if(!(inputGeneCount.get(gene.key) >= desiredGeneCount.get(gene.key))) {
                flag = false
            }
        }
        return flag
    }

    // Find new crossbread genes based off of 5 genes
    static def calculateDefinedGenePool(def genePool) {
        def newGene = []
        def geneTotal = [type: '', weight: 0]
        //Loop through all 5 genes and add totals
        for(int i = 0; i < genePool.size(); i++) {
            def genes = [Y: 0, G: 0, H: 0, X: 0, W: 0]
            genePool.each { geneList ->
                //Add each gene in the column to calculate new gene
                Gene gene = new Gene(geneList[i])
                genes[gene.type] += gene.weight
            }
            // Calculate new gene for each column of genes
            newGene += getNewGene(genes)
        }
        //println(newGene)
        return newGene
    }

    //Calculate the new gene based off the current column
    static def getNewGene(def genes) {
        def newGeneType = ''
        def newGeneTotal = 0
        genes.each { gene ->
            if(gene.getValue() > newGeneTotal) {
                newGeneType = gene.getKey()
                newGeneTotal = gene.getValue()
            } else if(gene.getValue() == newGeneTotal) {
                newGeneType += gene.getKey()
            }
        }
        return newGeneType
    }
}
