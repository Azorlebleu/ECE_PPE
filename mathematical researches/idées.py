#prendre tous les patients 

#patient : [heure min, heure max, adresse, temps passé]

#faire un tableau avec toutes les heures (heures min, heures max), trié 
"""
tri de [minA, maxA, minB, maxB, ...]
soit 
[
    [0,10],
    [10,20],
    ...
]
"""

#entre chaque intervalle du tableau:
    
    #voir quels sont les patients concernés  
    #de la forme : 
    """
    [
        [A,B],
        [A,B,E],
        ...
    ]
    """
    
    #essayer toutes les possibilités sur chaque intervalle (incluant les possibilités qui ne prennent pas tous les éléments en compte
    #(à partir de tous les strings de l'intervalle d'avant)
    #de la forme : 
    """
    [
        [
            [A],
            [A,B,E],
            [A,E,B],
            ...
        ],
        [...],
        ...
    ]
    """
    
    #appel API pour les distances dont on a besoin
    """
    Dans notre exemple : 
    on demande les distances entre
    A et B
    B et E
    A et E 
    E et B
    (si deux fois la meme requete -> ne pas la faire)
    """
    
    #Si elimination possible (pour len(string) >= 4 ), éliminer les strings inutiles
    """
    Du genre 
    Si 
    AEBC = 10 
    ABEC = 15
    Eliminer ABEC 
    une string est inutile si elle comporte les memes lettres qu'une autre string, que le début et la fin sont identiques, et que son temps est supérieur à cette autre string similaire
    
    Supprimer les strings qui font sortir du planning (qui ne respectent pas les conditions du patients)
    """
    