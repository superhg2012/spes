/**
 * Define ExtJSUtil Object to provide some common methods
 */

var ExtJSUtil = (function (){
	var my = {};

	my.getRawSheetType = function(sheetType) {
		if (sheetType == "month") {
			return '月度考核';
		} else if (sheetType == 'quarter') {
			return '季度考核';
		} else if (sheetType == 'year') {
			return '年度考核';
		}
	};
	
	return my;
}());