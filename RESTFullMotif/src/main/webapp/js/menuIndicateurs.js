/*******************
* déroulement/enroulement du menu contextuel
*******************/

window.onload=initMenu;
function initMenu() {	
	for (var i = 1; i<=20; i++) {
		if (document.getElementById('smenu'+i)) {
			document.getElementById('smenu'+i).style.display='none'; 
			document.images['bt'+i].src="images/menu_btPlus.gif";
			document.getElementById('menu'+i).style.background='#f5f5f5';
		}
	}
	
	document.images['bt2'].src="images/menu_btMoins.gif"; 
	document.getElementById('menu2').style.background='#ffffff';
	document.getElementById('smenu2').style.display='block';
}
function montreMenu(id) {
	var d = document.getElementById(id);
	var numId = d.id;
	var l = numId.length;
	var n = numId.substring(5,l);
	
	for (var i = 1; i<=20; i++) {
		if(i!=n){
			if (document.getElementById('smenu'+i)) {
				document.getElementById('smenu'+i).style.display='none'; 
				document.images['bt'+i].src="images/menu_btPlus.gif";
				document.getElementById('menu'+i).style.background='#f5f5f5';
			}
		}else{
			document.getElementById('menu'+n).style.background='#ffffff';
		}
	}
	
	var etat = d.style.display;
	if (d) {
		if (etat == "none"){	
			d.style.display='block';
			document.images['bt'+n].src="images/menu_btMoins.gif"; 
			document.getElementById('menu'+n).style.background='#ffffff';
		}else {
			d.style.display='none';
			document.images['bt'+n].src="images/menu_btPlus.gif";
			document.getElementById('menu'+n).style.background='#f5f5f5';
		}
	}
}