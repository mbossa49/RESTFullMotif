/**
 * La fonction qui permet de créer dynamiquement la popIn
 */
function createPopIn(data) {
	/*
	 * L'alphabet. 
	 */
	var lettreAlphabet = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
	
	for(var j = 0; j < 24; j++){
		
		var lettreEncours = lettreAlphabet[j];
		
		if(data[lettreEncours].length > 0){
	    	var listMotif = data[lettreEncours];
	    	var taille = data[lettreEncours].length;
			var elmt = '<li><a href="#'+lettreEncours+'">'+lettreEncours+'</a></li>';
			
			if(i==0){
				document.getElementById('alphabet').innerHTML = elmt;
			}else {
				document.getElementById('alphabet').innerHTML += elmt;
			}
			
	    	/* Remplissage des libellés*/		            	
	    	divLettre = document.createElement("div");
	    	divLettre.setAttribute("id", lettreEncours);
	    	divLettre.setAttribute("class", "lettre");
	    	
	    	divLettreSelectionnee = document.createElement("div");
	    	divLettreSelectionnee.setAttribute("class","lettreSelectionnee");
	    	divLettreSelectionnee.innerHTML=lettreEncours;
	    	divLettre.appendChild(divLettreSelectionnee);
	    	
	    	
	    	ulLettre = document.createElement("ul");
	    	ulLettre.setAttribute("class","listeMotifs");
	    	//---------------- foreach block -------------------------------------//		            	
	    	for(var i=0; i<taille; i++){
				liLettre = document.createElement("li");
				liLettre.setAttribute("onClick","selectionMotif(this)");
				liLettre.setAttribute("id",listMotif[i].label);

				text = listMotif[i].value;

	        	textNode = document.createTextNode(text);
	        	liLettre.innerHTML=text;
	        	ulLettre.appendChild(liLettre);
	    	}
	    	//--------------- Fin block foreach ---------------------------------------//
	    	
	    	divLettre.appendChild(ulLettre);
	    	blocMotifsDiv = document.getElementById('blocMotifs');
	    	blocMotifsDiv.appendChild(divLettre);
	    	
		}else {
			var elmt = '<li class="inactive">'+lettreEncours+'</li>';
			if(i==0){
				document.getElementById('alphabet').innerHTML = elmt;
			}else {
				document.getElementById('alphabet').innerHTML += elmt;
			}
		}
	}
}