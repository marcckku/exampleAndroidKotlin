************************************    mvvm_room_states_fixed    ************************************
Su questa versione vediamo la impostazione di Room che è un Database.
che  wrappa SQLite, quindi è un db di più alto livello.

E ci concentriamo anche sulla creazione di stati per gestire in maniera dinamica
i vari cambiamenti della nostra Applicazione.

Le classi al momento coinvolte sono soltanto :
ListUserDetailsSettingsActivity e UserDetailsSettingActivity
************************************    mvvm_room_states_fixed    ************************************

#Problema:
 *I dati nella lista quando si torna indietro da UserDetailsSettingsActivity
 *a ListUserDetailsSettingsActivity non vengono aggiornati.
 
#Soluzione : 
Utilizzo di Flow, cerca questa funzione :
--------------------------------------------------------  
		fun getAllUsersFlow(): Flow<Array<User>> {
			return db!!.userDaoCRUD().getAllUsersFlow()
		}
--------------------------------------------------------
##### Ma cos'è Flow? #####
*Nelle coroutine, un Flow è un tipo che può emettere più valori in sequenza, 
*invece di sospendere le funzioni che restituiscono un solo valore. 
*Ad esempio, puoi usare un Flow per ricevere aggiornamenti in tempo reale da un database. 
*Grazie alle coroutine, i flussi possono produrre dati anche in modo asincrono.

*Per ottenere tutti i valori dallo stream man mano che vengono emessi, usa collect.
*Poiché collect è una funzione di sospensione, deve essere eseguita all'interno di una coroutine. 
*Prende un valore lambda come parametro che viene chiamato su ogni nuovo valore. 
*Inoltre, poiché si tratta di una funzione di sospensione "suspend", la coroutine che chiama collect 
*può essere sospesa fino alla chiusura del flusso.

#Continua lettura sul Flow nel Manuale Ufficiale Android:
https://developer.android.com/kotlin/flow#:~:text=In%20coroutines%2C%20a%20flow%20is,live%20updates%20from%20a%20database.&text=Thanks%20to%20coroutines%2C%20flows%20can%20also%20produce%20data%20asynchronously.


#Qui troverai i motivi del utilizzo di LiveData al posto di RxJava e perchè!!
https://proandroiddev.com/how-not-to-use-sealed-classes-and-livedata-for-state-management-4bfcaf314e96

Il framework fornisce già strumenti simili per i casi più comuni.
    - Invece di usare RxJava, si puo' usare LiveData per l'osservabilità dei dati.
    - Per il lavoro asincrono, userò Coroutines.
	
##Creo stati per gestire gli oggeti osservati
CI SONO 3 STATI NELLA VISTA UTENTE

1) CONTENT (STATO IN CARICO I DATI)
2) LOADING (STATO CARIMENTO)
3) ERROR   (GESTIONE ERRORE A CARICO DEL PROGRAMMATORE

Stati sono gestiti dalle classi Sigilate o Sealed (https://kotlinlang.org/docs/sealed-classes.html)

	
	
	
