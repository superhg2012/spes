/**
 * Define ExtJSUtil Object to provide some common methods
 */

var ExtJSUtil = (function (){
	var my = {};

	my.getRawSheetType = function(sheetType) {
		if (sheetType == "month") {
			return '�¶ȿ���';
		} else if (sheetType == 'quarter') {
			return '���ȿ���';
		} else if (sheetType == 'year') {
			return '��ȿ���';
		}
	};
	
	return my;
}());