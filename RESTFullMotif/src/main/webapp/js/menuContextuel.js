/*******************
* déroulement/enroulement du menu contextuel
*******************/

window.onload=montreMenu;
function montreMenu(id) {
	var d = document.getElementById(id);
	for (var i = 1; i<=20; i++) {
		if (document.getElementById('smenu'+i)) {
			document.getElementById('smenu'+i).style.display='none'; 
			document.images['bt'+i].src="images/menu_btPlus.gif";
			document.getElementById('menu'+i).style.background='#F5F5F5'; 
		} 
	}
	if (d) {
		d.style.display='block';
		var numBt = d.id;
		var l = numBt.length;
		var n = numBt.substring(5,l);
		document.images['bt'+n].src="images/menu_btMoins.gif"; 
		document.getElementById('menu'+n).style.background='#ECEDEE'; 
	}
}