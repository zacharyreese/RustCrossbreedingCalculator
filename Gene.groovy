class Gene {
    String type
    double weight

    Gene(String typeString) {
        typeString = typeString.toUpperCase()
        if(typeString == 'Y') {
            type = 'Y'
            weight = 0.5
        } else if (typeString == 'G') {
            type = 'G'
            weight = 0.5

        } else if (typeString == 'H') {
            type = 'H'
            weight = 0.5

        } else if (typeString == 'X') {
            type = 'X'
            weight = 0.9

        }else if (typeString == 'W') {
            type = 'W'
            weight = 0.9
        }
    }
}
