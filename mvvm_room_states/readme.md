************************************    mvvm_room_states    ************************************
Su questa versione vediamo la impostazione di Room che è un Database.
che  wrappa SQLite, quindi è un db di più alto livello.

E ci concentriamo anche sulla creazione di stati per gestire in maniera dinamica
i vari cambiamenti della nostra Applicazione.

Le classi al momento coinvolte sono soltanto :
ListUserDetailsSettingsActivity e UserDetailsSettingActivity
************************************    mvvm_room_states    ************************************


1) Problemi su questa Versione "mvvm_room_states"
 I dati aggiornati nella lista quando si torna indietro da UserDetailsSettingsActivity
 a ListUserDetailsSettingsActivity non vengono aggiornati i dati user nella Lista.

Possibile Soluzione tratto da : https://proandroiddev.com/how-not-to-use-sealed-classes-and-livedata-for-state-management-4bfcaf314e96
Usare una sola classe per l'intero stato da vedere nella seguente version "mvvm_room_states_fixed"


Qui troverai i motivi del utilizzo di LiveData al posto di RxJava e perchè!!
https://proandroiddev.com/how-not-to-use-sealed-classes-and-livedata-for-state-management-4bfcaf314e96

Il framework fornisce già strumenti simili per i casi più comuni.
    - Invece di usare RxJava, si puo' usare LiveData per l'osservabilità dei dati.
    - Per il lavoro asincrono, userò Coroutines.
