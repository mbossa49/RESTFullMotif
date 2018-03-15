	var fenetre;
	
	function openPopup(url)
	{
		//Fonction d'ouverture de popup
  		fenetre = window.open(url, "", "width=475,height=400,left=100,top=100,scrollbars=yes,location=no,menubar=no,toolbar=no, status=no, resizable=no");
  		//fenetre.focus();
	}
	
	function openPopupDimentionnee(url,width,height)
	{
		//Fonction d'ouverture de popup
  		fenetre = window.open(url, "nul", "width=" + width + " ,height=" + height + ",left=100,top=100,scrollbars=0,directories=0,location=0,menubar=0,toolbar=0,status=0,resizable=0");
  		//fenetre.focus();
	}
	
	function openPopupDimentionneeScroll(url,width,height)
	{
		//Fonction d'ouverture de popup
  		fenetre = window.open(url, "", "width=" + width + " ,height=" + height + ",left=100,top=100,scrollbars=yes,location=no,menubar=no,toolbar=no, status=no, resizable=no");
  		//fenetre.focus();
	}
	
	function openPopupFullScreen(url)
	{
		//Fonction d'ouverture de popup
  		fenetre = window.open(url, "", "scrollbars=yes,location=no,menubar=no,toolbar=no, status=no, resizable=no");
  		fenetre.moveTo(0,0); 
		fenetre.resizeTo(fenetre.screen.availWidth, fenetre.screen.availHeight);
  		//fenetre.focus();
	}
	
	function openFormPopup(form)
	{
		// Fonction d'ouverture de popup pour un form
		lForm = form.action;
		
		var lChildNodeArray = form.childNodes;
		var imprimer = false;
		for(var i=0; i < lChildNodeArray.length;i++) {
			var lChildNode = lChildNodeArray[i];
			if (lChildNode.type != undefined && lChildNode.type == "hidden") {
				if(lChildNode.name == "imprimer"){
					imprimer = true;
				}else{
					if (lForm.indexOf("?")>=0) {
						lForm += "&";
					} else {
						lForm += "?";
					}
					lForm += lChildNode.name;
					lForm += "=";
					// Encodage de la valeur au format URL encoded
					lForm += escape(lChildNode.value);
				}
			}
		}
		fenetre = window.open(lForm, "popup", "width=800,height=600,left=100,top=100,menubar=no,toolbar=no,location=no,status=no,scrollbars=no,resizable=no");
		if(imprimer){
		
			fenetre.onload = function(){
				setTimeout('fenetreI()',1000);
			}
		}
	}
	
	function fenetreI(){
		fenetre.window.print();
	}
	
	function openFormXLS(form)
	{
	// Fonction d'ouverture de popup pour un form
	   form = eval(form)
//	   form.target = "popup";
	   form.submit();	   
	}
	
	function openFichier(form)
	{
		// Fonction d'ouverture de popup pour un form
	   //form = eval(form)
	   form.submit();	   
	}
	