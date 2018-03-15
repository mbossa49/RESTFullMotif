
window.onload=plierDeplier;

var z = 0;/*z = bloc ouvert ou non*/
var y = 0;/*y = numéro du bloc ouvert*/

function plierDeplier(id)
{				
	for (var i = 1; i<=20; i++) {
		if (document.getElementById('bloc'+i)) {
			document.getElementById('bloc'+i).style.display="none";
			document.getElementById('lien'+i).innerHTML='Déplier';
			document.getElementById('lien'+i).style.background='url("images/plierDeplier_plus.gif") no-repeat top right';
		}
	}
	var d = document.getElementById(id);
	if (d) {				
		var num = d.id;
		var l = num.length;
		var n = num.substring(4,l);
		if(z==0){
			d.style.display="block";
			document.getElementById('lien'+n).innerHTML='Plier';
			document.getElementById('lien'+n).style.background='url("images/plierDeplier_moins.gif") no-repeat top right';
			z = 1;
			y = n;
		}else if((z==1)&&(y==n)){
			d.style.display="none";
			document.getElementById('lien'+n).innerHTML='Déplier';
			document.getElementById('lien'+n).style.background='url("images/plierDeplier_plus.gif") no-repeat top right';
			z = 0;
			y = 0;
		}else if((z==1)&&(y!=n)){
			d.style.display="block";
			document.getElementById('lien'+n).innerHTML='Plier';
			document.getElementById('lien'+n).style.background='url("images/plierDeplier_moins.gif") no-repeat top right';
			z = 0;
			y = n;
		}
	}
	
}