function outputSubject() {
			/* HTML id=my_text를 자바스크립트의 objText로 선언하고 연결
			입력한 문자(objText.value)을 targetText에 넣어준다. */
			var objText = document.getElementById("my_text");
			var targetText = objText.value;
			
			/* HTML id=color를 자바스크립트의 objColor로 선언하고 연결
			targetText.fontcolor()를 targetText에 넣어준다. */
			
			var objColor = document.getElementById("color");
			targetText = targetText.fontcolor(
					objColor.options[objColor.selectedIndex].value);
			
			/* HTML id=size를 자바스크립트의 objSize로 선언하고 연결
			targetText.fontsize()를 targetText에 넣어준다. */
			var objSize = document.getElementById("size");
			targetText = targetText.fontsize(
					objSize.options[objSize.selectedIndex].value);
			
			/* HTML id=font를 자바스크립트의 objOptions로 선언하고 연결
			if문으로 checked되었다면 사용 */
			var objOptions = document.getElementsByName("font");
			if(objOptions[0].checked) {
				targetText = targetText.strike(); 
			}
			
			if(objOptions[1].checked) {
				targetText = targetText.big(); 
			}
			
			if(objOptions[2].checked) {
				targetText = targetText.small(); 
			}
			
			if(objOptions[3].checked) {
				targetText = targetText.bold(); 
			}
			
			if(objOptions[4].checked) {
				targetText = targetText.italics(); 
			}
			
			if(objOptions[5].checked) {
				targetText = targetText.sup(); 
			}
			
			if(objOptions[6].checked) {
				targetText = targetText.sub(); 
			}
			
			if(objOptions[7].checked) {
				targetText = targetText.toLowerCase(); 
			}
			
			if(objOptions[8].checked) {
				targetText = targetText.toUpperCase(); 
			}
			
			/* 출력할 HTML id=result를 자바스크립트의 objResult로 선언하고 연결
			targetText를 innerHTML이용해 출력 */
			var objResult = document.getElementById("result");
			objResult.innerHTML = targetText;
		}