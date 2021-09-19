//---选择所有的选项------------------------
function selectAll() {
	var ado = form2.ado;
	for (i = 0; i < ado.length; i++) {
		ado.options[i].selected = true;
	}
}
//---移动所有选项到右边---------------------
function AllToRight() {
	var mae = form2.mae;
	var ado = form2.ado;
	for (i = 0; i < mae.length; i++) {
		var oAdd = document.createElement("option");
		oAdd.text = mae.options[i].text;
		oAdd.value = mae.options[i].value;
		ado.add(oAdd);
	}

	for (i = mae.length; i > -1; i--) {
		mae.remove(i);
	}
}
//---移动所有选项到左边---------------------
function AllToLeft() {
	var mae = form2.mae;
	var ado = form2.ado;
	for (i = 0; i < ado.length; i++) {
		var oAdd = document.createElement("option");
		oAdd.text = ado.options[i].text;
		oAdd.value = ado.options[i].value;
		mae.add(oAdd);
	}

	for (i = ado.length; i > -1; i--) {
		ado.remove(i);
	}
}
//---移动单个选项到右边---------------------
function ToRight() {
	var mae = form2.mae;
	var ado = form2.ado;
	for (i = 0; i < mae.length; i++) {
		if (mae.options[i].selected) {
			var oAdd = document.createElement("option");
			oAdd.text = mae.options[i].text;
			oAdd.value = mae.options[i].value;
			ado.add(oAdd);
		}
	}

	for (i = mae.length - 1; i > -1; i--) {
		if (mae.options[i].selected) {
			mae.remove(i);
		}
	}
}
//---移动单个选项到左边---------------------
function ToLeft() {
	var mae = form2.mae;
	var ado = form2.ado;
	for (i = 0; i < ado.length; i++) {
		if (ado.options[i].selected) {
			var oAdd = document.createElement("option");
			oAdd.text = ado.options[i].text;
			oAdd.value = ado.options[i].value;
			mae.add(oAdd);
		}
	}
	for (i = ado.length - 1; i > -1; i--) {
		if (ado.options[i].selected) {
			ado.remove(i);
		}
	}
}
