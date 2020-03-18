import itertools
import numpy as np
import time
import random as rd
import matplotlib.pyplot as plt
tableau_patients = []
x = 0
n = 25
pos = []
tableau_patients.append( [[x, 5, 4, 1, 4, 7, 4, 2, 1, 8, 2], [0, 20]])
tableau_patients.append( [[5, x, 3, 4, 2, 8, 1, 5, 5, 6, 9], [0, 30]])
tableau_patients.append( [[4, 3, x, 4, 3, 2, 7, 9, 10, 1, 2], [30, 40]])
tableau_patients.append( [[1, 4, 4, x, 5, 6, 5, 3, 4, 1, 4], [20, 40]])
tableau_patients.append( [[4, 2, 3, 5, x, 2, 4, 7, 8, 2, 1], [10, 20]])
tableau_patients.append( [[7, 8, 2, 6, 2, x, 1, 3, 4, 5, 4], [60, 70]])
tableau_patients.append( [[2, 3, 4, 5, 8, 1, x, 8, 2, 4, 5], [20, 50]])
tableau_patients.append( [[6, 2, 6, 3, 4, 1, 5, x, 0, 6, 5], [30, 50]])
tableau_patients.append( [[7, 0, 1, 2, 11, 7, 12, 4, x, 9, 6], [50, 70]])
tableau_patients.append( [[9, 10, 11, 10, 3, 12, 1, 5, 3, x, 1], [30, 50]])
tableau_patients.append( [[5, 3, 2, 0, 4, 12, 12, 11, 9, 11, x], [20, 50]])

def make_random_patients(n):
    max_time=420
    for i in range(n):
        distances = []
        a = rd.randint(0, max_time)
        b = 30
        if (a - b < 0 ):
            lower_bound = 5
        else:
            lower_bound = a - b
        
        if (a + b > max_time) :
            upper_bound = max_time
        else:
            upper_bound = a + b
        for j in range(n):
            distances.append(rd.randint(2,10))
        tableau_patients.append([distances, [lower_bound, upper_bound] ])
#make_random_patients(n)


tableau_patients = []
x = 0
n = 25

debut_int=0
fin_int=0
milieu_int=0
valeur_intermediaire=0
largeur = 3 #largeur en quart d'heure
nb_patients=10
heures_de_taf = 6       
#fixed_windows(heures_de_taf,nb_patients,largeur)
print(tableau_patients)

randomlist=[]
def fixed_windows (heure_de_taf, nb_patient, largeur, variance):
    # nombre aleatoire entre 0 et n
    #nb de quart d'heure de taf dans par exemple 6 heures de taf
    quart_heure_taf=heure_de_taf*4
    #tant que on a pas le nb de patient voulu on continue
    tab=[]
    for i in range(nb_patient):
        tab.append(rd.randint(2,7))
    for i in range (nb_patient):    
        alpha= rd.randint(0,variance)
        largeur_int= largeur + alpha
        #valeur aléatoire qui va prendre une valeur entre le 0 et le dernier quart d'heure possible 
        milieu_int=round(rd.random()*quart_heure_taf)
        # on transforme la valeur intermediaire en minutes 
        debut_int=milieu_int-round(largeur_int/2)#on def le debut de l'intervalle (largeur_int en minutes)
        fin_int=milieu_int+round(largeur_int/2) 
        if (debut_int<0):
            debut_int=0
        elif (fin_int>quart_heure_taf):
            fin_int=quart_heure_taf
        #print("debut_int is", debut_int)
        #print("fin_int is", fin_int)
        debut_int*=15
        fin_int*=15
        
        tableau_patients.append([tab,[debut_int, fin_int]])
    tableau_patients[0][1][0] = 0
    tableau_patients[0][1][1] = 15

def plot(heure_de_taf, N, largeur, variance):
    fixed_windows(heure_de_taf, N, largeur, variance)
    print(tableau_patients)
    i=0
    left=[]
   # left = [random.randint(0,22) for x in range(N)]
    for line in tableau_patients:
       left.append(line[1][0])
       randomlist.append(line[1][1]-line[1][0])
    #print ("randomlist=",randomlist)
    #print ("      left=",left)
    
    for i in range(0,N):
        pos.append(i+1)
        if (randomlist[i]+left[i]>heures_de_taf*60):
            randomlist[i]=heures_de_taf*60-left[i]
    height = randomlist
    width = 0.8
    print ("POS ET HEIGHT", len(pos), len(height))
    axes = plt.gca()
    axes.yaxis.set_ticks(range(N))
    axes.xaxis.set_ticks(range(heures_de_taf*60))
    plt.barh(pos, height, width, left, color='red' )
    #plt.xlim(0,max(height)+1)
    plt.title('Variable patient.')
    plt.savefig('barchart_horizontal_matplotlib.png')
    plt.show()


def trouver_min():
    min = res[0][1]
    for i in range(len(res)-1):
        if res[i][1] < min:
            min = res[i][1]
    return min
    
    
def tableau_imperatifs():
    #créer le tableau avec tous les impératifs de temps
    tab = []
    for i in range (len(tableau_patients)):
        tab.append(tableau_patients[i][1][0])
        tab.append(tableau_patients[i][1][1])

    #enlever les doublons 
    tab =list(set(tab))
    
    #trier le tableau
    list.sort(tab)

    #renvoyer sous la forme de différents intervalles
    tab2 = []
    for i in range (len(tab)-1):
        tab2.append( [tab[i], tab[i+1] ])
        
    return tab2
    
def quels_patients(tableau_imperatifs):
    #met les patients qui sont soignables dans chaque intervalle
    tab = []
    for i in range(len(tableau_imperatifs)):
        tab_prov = []
        for j in range(len(tableau_patients)):
            if ((tableau_patients[j][1][0] <= tableau_imperatifs[i][0]) & (tableau_patients[j][1][1] >= tableau_imperatifs[i][1])):
                tab_prov.append(j)
        tab.append(tab_prov)
        
    return(tab)
    
    
def fact(n):
    """fact(n): calcule la factorielle de n (entier >= 0)"""
    if n<2:
        return 1
    else:
        return n*fact(n-1)
def nb_possibilites(n):
    sm=0
    for k in range(n+1):
        sm+= fact(n)/fact(n-k)
    return sm
    
def all(n):
    for i in range(n):
        (nb_possibilites(i),fact(i), nb_possibilites(i)/fact(i))
        
        
        
def permutliste(sous_listes, er=False):
    """
    Retourne la liste de toutes les permutations, toutes différentes deux à deux, d'une liste de séquences
    Par exemple, si on utilise cela sur a = [ [1,2,3], [4,5] ] alors
    permutliste(a) = [[],
 [1, 2, 3],
 [2, 3, 1],
 [3, 1, 2],
 [1, 3, 2],
 [2, 1, 3],
 [3, 2, 1],
 [4, 5],
 [5, 4]]

    """
    tab = []
    tab_final = []
    tab.append([])
    for elem in sous_listes:
        if len(elem) <=1:
            tab.append(elem)
    
    for i in range (len(sous_listes)):
        seq=sous_listes[i]
        p = [seq]
        n = len(seq)
        for a in range(0,n-1):
            for b in range(0, len(p)):
                z = p[b][:]
                
                for c in range(0,n-a-1):
                    z.append(z.pop(a))
                    if er==False or (z not in p):
                        p.append(z[:])
            tab.append(p)
            
    for elem in tab:
        if len(elem)>1:
            for elem2 in elem:
                if (elem2 not in tab_final):
                    tab_final.append(elem2)
        else:
        
            if (elem not in tab_final):
                    tab_final.append(elem)
    
    return tab_final


def sous_listes(seq):
    #sort toutes les sous-listes possibles à partir d'une liste existante. 
    """
    Par exemple :
    si seq = [1,2,3]
    alors sous_listes(seq) = [[1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]
    """
    tab1=[]
    for t in itertools.chain(*(itertools.combinations(seq, long) for long in range(1, len(seq)+1))):
        tab1.append(list(t))
    return(tab1)

def retirer_sequences_patients_oublies(tab, borne_sup, patients):
    #vérifier que si c'est le dernier intervalle possible pour le patient, il soit forcément inclus dans la liste
    a_enlever = []
    #on parcourt tous les patients et vérifie si il s'agit du dernier intervalle pour ce patient
    for j in range(len(patients)):
        #si c'est le cas, on enleve les strings qui ne contiennent pas ce patient 
        if patients[j][1][1] == borne_sup:
            for i in range(len(tab)):
                if j not in tab[i]:
                    if (i not in a_enlever):
                        a_enlever.append(i)
    a_enlever.sort()
    for i in range(len(a_enlever)):
        tab.pop(a_enlever[-i-1])
    
    return(tab)
                    
            
    
    return(0)
    
    
def toutes_possibilites(seq, borne_sup):
    #prend toutes les sous listes, puis les permute toutes 
    tab = sous_listes(seq)
    tab = permutliste(tab)

    return(tab)



def creer_toutes_nouvelles_possibilites(tab_avant, all_routes):
    #crée toutes les possibilités pour le nouvel intervalle de temps en fonction de celui précédent
    nouveau_tab = []
    tab_prov = []
    for avant in tab_avant:
        #on regarde toutes les possibilités de l'intervalle d'avant, et on greffe si on le peut les nouvelles possibilités du nouvel intervalle
        #si on le peut = si il n'y a pas deux fois le même patient dans la liste.
        for possibilites in all_routes:
            
            mettre = 1
            for patient in possibilites:
                if patient in avant:
                    mettre = 0
                    
                    #il suffit qu'un seul patient soit bloquant pour arrêter la recherche
                    break 
            
            if (mettre):
                tab_prov = avant + possibilites
                
                #pour éviter les doublons
                if tab_prov not in nouveau_tab:
                    nouveau_tab.append(tab_prov)

    return(nouveau_tab)

def list_voeu (possibilites_intervalle):
    #ajoute dans une liste les distances dont on a besoin pour calculer le temps demander 
    """Par exemple, si les possibilités d'un intervalle sont
    a =[[],
 [1],
 [2],
 [3],
 [1, 2],
 [2, 1],
 [1, 3],
 [3, 1],
 [2, 3],
 [3, 2],
 [1, 2, 3],
 [2, 3, 1],
 [3, 1, 2],
 [1, 3, 2],
 [2, 1, 3],
 [3, 2, 1]]
 alors list_voeu(a) = [[1, 2], [2, 1], [1, 3], [3, 1], [2, 3], [3, 2]]
 """
    list_voeux=[]
    for i in range (len(possibilites_intervalle)):
        for j in range (len(possibilites_intervalle[i])-1):
            if [possibilites_intervalle[i][j],possibilites_intervalle[i][j+1]] not in list_voeux:
                list_voeux.append([possibilites_intervalle[i][j],possibilites_intervalle[i][j+1]])
    #print(len(list_voeux))
    return list_voeux
    
        
def matrice_dist (liste_voeux, M):
    #Remplit la matrice des distances en fonction de celles demandées dans une liste de voeux pour un certain intervalle

    for elem in liste_voeux:
        x1=elem[0]
        x2=elem[1]
        if M[x1][x2]==0:
            M[x1][x2]=appel_dist(x1,x2)
    return(M)
    
def appel_dist (p1, p2):
    #fait l'appel API de la distance entre deux patients
    return 5
    
def test_similaire(seq1, seq2):
    #définit si deux séquences sont similaires.
    #On considère deux séquences similaires ssi :
        #len(seq1) = len(seq2)
        #seq1[0] = seq2[0] et seq1[-1] = seq2[-1] 
        #seq1 et seq2 utilisent les mêmes caractères, tous distincts deux à deux (cette deuxième condition n'a pas a être vérifiée, par construction des séquences données à la fonction)
    #On note également que pour que deux séquences soient similaires, il faut que len(seq1) >=4 et len(seq2) >=4
    #return True ou False
    
    #len(seq1) >=4 et len(seq2) >=4
    if len(seq1)<4 or len(seq2)<4:
        return False
        
    #len(seq1) = len(seq2)
    if len(seq1) != len(seq2):
        return False
        
    #seq1[0] = seq2[0] et seq1[-1] = seq2[-1] 
    if (seq1[0] != seq2[0]) or (seq1[-1] != seq2[-1]) :
        return False
    
    #seq1 et seq2 utilisent les mêmes caractères
    for i in range(1, len(seq1)-1):
        if seq1[i] not in seq2:
            return False
    
    return True
    
def calc_temps(M,seq):
    """
    à rajouter le temps de soin !
    """
    #retourne le temps mis pour le trajet lié à une séquence
    somme=0
    temps_soin=5
    for i in range (len(seq)-1):
        somme+=M[seq[i]][0][seq[i+1]]
        somme+=temps_soin
    return somme
    
def retirer_similaires_plus_longs(tab):
    temps_debut = 0
    a_enlever = []
    for i in range(len(tab)):
        for j in range(i+1,len(tab)):
            
            #temps : 4
            a = test_similaire(tab[i],tab[j])
            
            if a:
                #temps : 1.5
                d1 = calc_temps(tableau_patients, tab[i])
                d2 = calc_temps(tableau_patients, tab[j])
                
                
                #temps : 8
                if d1 > d2:
                    a_enlever.append(i)
                if d2 > d1:
                    a_enlever.append(j)

    #temps : négligeable
    a_enlever = list(set(a_enlever))
    a_enlever.sort()
    for i in range(len(a_enlever)):
        tab.pop(a_enlever[-i-1])
    return(tab)
                        
def show_similaire(tab):
    start_time = time.time()
    tab2 = []
    for i in range(len(tab)):
        for j in range(i+1, len(tab)):
            print(i, ":", " ",tab[i]," ",j, " ", tab[j], test_similaire(tab[i],tab[j]))
            if test_similaire(tab[i],tab[j]):
                tab2.append([tab[i],tab[j]])
    print("Temps d execution : %s secondes ---" % (time.time() - start_time))
    return(tab2)
    
def calc_similaire(tab):
    nb=0
    start_time = time.time()
    tab2 = []
    for i in range(len(tab)):
        for j in range(i+1, len(tab)):
            if test_similaire(tab[i],tab[j]):
                tab2.append([tab[i],tab[j]])
                nb+=1
    print("Temps d execution : %s secondes ---" % (time.time() - start_time))
    return tab2, nb
    
"""   
a = toutes_possibilites([1,2,3,4])
show_similaire(a) #temps d'execution tres long ! mais seulement à cause du print (comparaison : 18s avec print, 0 sans (lol)
"""
def test_realistes(seq, imperatifs):
    #une séquence est non réaliste ssi:
        #1) si pour chaque patient de la séquence, le temps mis jusqu'à ce patient ne respecte pas sa plage horaire (+temps soin)
        #2) si il manque un patient dans la séquence, et que ce patient aurait déjà du être traité
        
    #1)
    for i in range(len(seq)):
        #si le temps mis jusqu'à ce patient dépasse la fin de la plage horaire, False
        print (seq[0:i+1], "temps de la sequence :", calc_temps(tableau_patients, seq[0:i+1]), "imperatif :", tableau_patients[seq[i]][1])
        if calc_temps(tableau_patients, seq[0:i+1]) > tableau_patients[seq[i]][1][1]:
            print("enlever : trop tard !")
            return False
        if calc_temps(tableau_patients, seq[0:i+1]) < tableau_patients[seq[i]][1][0]:
            print("enlever : trop tot !")
            return False
    
def supprimer_sequences_non_realistes(tab):
    for i in range(len(tab)):
        test_realistes(tab[i], 0)
        
        
def main():
    start_time = time.time()
    similaire_time = 0
    #prend tous les impératifs en un tableau d'intervalles
    imperatifs = tableau_imperatifs()
    #prend tous les patients pour chaque intervalle
    tous_patients = quels_patients(imperatifs)
    #on commence toujours du premier point
    sequence_ancien_intervalle = [ [0] ]
    #liste des voeux à calculer par l'API
    voeux = []
    #matrice des distances
    M = np.zeros((n,n))
    mult = 1
    print("Imperatifs de temps : ", imperatifs)
    for i in range (len(imperatifs)):
  
        intervalle = imperatifs[i]

        #prend la liste de patients pour cet intervalle
        patients = tous_patients[i]
        print("Nombre de patients dans cet intervalle :", len(tous_patients[i]))
        #calcule toutes les possibilites pour ces patients
        all_possibilites = toutes_possibilites(patients, intervalle[1])
        #greffe les possibilités aux séquences retenues du précédent intervalle
        
        sequence_ancien_intervalle = creer_toutes_nouvelles_possibilites(sequence_ancien_intervalle, all_possibilites)

        sequence_ancien_intervalle = retirer_sequences_patients_oublies(sequence_ancien_intervalle, intervalle[1], tableau_patients)
        #ajoute les distances dont on a besoin a une liste de voeux
        voeux = list_voeu(sequence_ancien_intervalle)
        #ajoute les distances dont l'on a besoin à une matrice comportant toutes les distances calculées jusque là
        M = matrice_dist(voeux, M)
        #parmi deux séquences similaires, ne garder que la plus rapide
        partial_time = time.time()
        sequence_ancien_intervalle = retirer_similaires_plus_longs(sequence_ancien_intervalle)
        similaire_time += time.time() - partial_time
        #supprimer les séquences où la plage horaire d'un patient n'est pas respectée   
        """
        supprimer_sequences_non_realistes()
        """ 
        print("Intervalle de temps considéré : ", intervalle)
        #print("Séquences possibles: ", sequence_ancien_intervalle)
        print("Nombre de séquences possibles :", len(sequence_ancien_intervalle))
        mult = mult + fact(len(tous_patients[i]))*(len(sequence_ancien_intervalle))
      
    min = calc_temps(tableau_patients,sequence_ancien_intervalle[0])
    res = [sequence_ancien_intervalle[0], min]
    for i in range(len(sequence_ancien_intervalle)):
        if calc_temps(tableau_patients,sequence_ancien_intervalle[i])<min:
            min = calc_temps(tableau_patients,sequence_ancien_intervalle[i])
            res = [sequence_ancien_intervalle[i], min]
    print("\n\nRESULTAT FINAL :",res)
    #print la matrice
    #print(M)
    print(fact(10)/(2*mult))
    print("Temps d execution : %s secondes ---" % (time.time() - start_time))
    print("Temps d execution de la fonction retirer_similaire: %s secondes ---" % (similaire_time))
    
    
    #Printing part
    fin_res = []
    height = []
    left = []
    width= 0.4
    for i in range (len(res[0])):
        fin_res.append(tableau_patients[res[0][i]])
        height.append( tableau_patients[res[0][i]][1][1] - tableau_patients[res[0][i]][1][0] )
        left.append( tableau_patients[res[0][i]][1][0] )
    
    plt.barh(pos, height, width, left, color='green' )
    #plt.xlim(0,max(height)+1)
    plt.title('Variable patient.')
    plt.savefig('barchart_horizontal_matplotlib.png')
    plt.show()
plot(6,10,5,2)  
main()