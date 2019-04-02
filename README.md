# Éta
---

#### Íslenska: ####
## Hugbúnaðarverkefni 2 - HBV601G
**Hópur 3**
* Aníta Kristjánsdóttir - ank16@hi.is
* Elvar Árni Sturluson - eas20@hi.is
* Tinna Sturludóttir - tis12@hi.is

### What is Éta?

Éta is a restaurant searcher. It is possible to search for restaurants by pressing the "Leita að veitingastað" (e. Search for a restaurant) button.
You are able to search either by name or by using a price range and a list of tags.

After submitting the search form by pressing the "Leita" (e. Search) button you are present with a list of appropriate restaurants.

If you select a restaurant from the list you go that restaurant's profile where you are able to get information about the restaurant. On the profile you are also able to view reviews about the restaurant or forge one of your own.

Users that register as "eigandi" (e. owner) are flagged as restaurant owners thus allowing them to register their restaurant.

### How to get the app?

1. Clone the repo from GitHub. 
2. Open the repo in Android Studio.
3. Run the app.

### Things to have in mind

The web service that handle the database goes to sleep every half an hour if have not received any request in that time.
If it receives a request while in sleep mode it will take a few seconds to wake up.
You can manually wake up the web service by going to the following link: 
[Wake up, pal](https://eta-bakendi.herokuapp.com/restaurant/11)

---

#### English:
## Software Project 2 - HBV601G
**Group 3**
* Aníta Kristjánsdóttir - ank16@hi.is
* Elvar Árni Sturluson - eas20@hi.is
* Tinna Sturludóttir - tis12@hi.is

### Hvað er Éta?

Éta er veitingastaðaleit. Það er hægt að leita að veitingastöðum með því að velja "Leita" hnappinn.  
Það er hægt að leita að veitingastað annað hvort eftir nafni eða með því að setja inn leitarskilyrði í síu.

Hægt er að velja veitingastað af lista út frá leitinni.

Þegar veitingastaður er valinn er hægt að sjá upplýsingar um staðinn eins og staðsetningu og umsagnir frá notendum. Hægt er að bæta inn umsögn.

Það er hægt að bæta veitingastað við appið. Til að gera það þarf notandi að búa til aðgang og vera "eigandi".

### Hvernig sæki ég appið?

1. Afritið gagnahirsluna frá GitHub.
2. Opnið gagnahirsluna í Android Studio.
3. Keyrið appið.

### Atriði til að hafa í huga

Ef bakendinn fær enga beðni í hálftíma þá fer hann að sofa. Þannig þegar það er næst sent beðni á hann þá getur það tekið 30sek að vakna. Hægt er að vekja hann með að opna eftirfarandi hlekk:
[Vaknaðu, stráksi](https://eta-bakendi.herokuapp.com/restaurant/11)

--- 

### UML Class Diagram

https://drive.google.com/file/d/1kEAvh-6kNAeiLPK7f-nJcCX1ene4i7Wr/view?usp=sharing

### UML Activity Diagram

https://drive.google.com/file/d/1UETcpGeXlmQKGzvhfet2DsfTWAfl_wv6/view?usp=sharing


