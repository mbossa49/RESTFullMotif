
window.onload=graphes_afficherCacher;

var z = 0;/*z = bloc ouvert ou non*/
var y = 0;/*y = numéro du bloc ouvert*/

function graphes_afficherCacher(id)
{				
	for (var i = 1; i<=20; i++) {
		if (document.getElementById('graphe'+i)) {
			document.getElementById('graphe'+i).style.display="none";
			document.getElementById('lienGraphe'+i).innerHTML='Afficher';
			document.getElementById('lienGraphe'+i).style.background='url("images/graphe_plus.gif") no-repeat top right';
		}
	}
	var d = document.getElementById(id);
	if (d) {				
		var num = d.id;
		var l = num.length;
		var n = num.substring(6,l);
		if(z==0){
			d.style.display="block";
			document.getElementById('lienGraphe'+n).innerHTML='Cacher';
			document.getElementById('lienGraphe'+n).style.background='url("images/graphe_moins.gif") no-repeat top right';
			z = 1;
			y = n;
		}else if((z==1)&&(y==n)){
			d.style.display="none";
			document.getElementById('lienGraphe'+n).innerHTML='Afficher';
			document.getElementById('lienGraphe'+n).style.background='url("images/graphe_plus.gif") no-repeat top right';
			z = 0;
			y = 0;
		}else if((z==1)&&(y!=n)){
			d.style.display="block";
			document.getElementById('lienGraphe'+n).innerHTML='Cacher';
			document.getElementById('lienGraphe'+n).style.background='url("images/graphe_moins.gif") no-repeat top right';
			z = 0;
			y = n;
		}
	}
	
}