/**
多选树状菜单
*/
function _clickCheckboxTreeNode( obj ) {

    var index = obj.sourceIndex;
    var isChecked = obj.checked;
    
    var currObj = obj;
    var currTd = document.all( index - 1 );
    var nextObj = document.all( index + 4 );
    var nextTd = document.all( index + 3 );

    var paddingWidth = currTd.style.paddingLeft;
    var nextPaddingWidth;
    
    //同步所有下级的被选中状态
    var i = index;
    while ( i > 0 ) {
        
        currObj = document.all( i );
        currTd = document.all( i - 1 );
        nextObj = document.all( i + 4 );
        nextTd = document.all( i + 3 );

        if ( nextObj.type.toUpperCase() == "HIDDEN" && nextObj.name == "_CheckboxTreeEnd" ) {
            break;
        }

        nextPaddingWidth = nextTd.style.paddingLeft;
		
        if ( nextPaddingWidth <= paddingWidth ) {
            break;
        }
        if(!nextObj.disabled)
        nextObj.checked = isChecked;
        i += 4;
    }


    currObj = obj;
    currTd = document.all( index - 1 );
    paddingWidth = currTd.style.paddingLeft;
    
    if (paddingWidth > "0px") {

        var preObj = document.all( index - 4 );
        var preTd = document.all( index - 5 );
        var prePaddingWidth;
        
        //同步父级的被选中状态
        var j = index;
		var currPaddingWidth="XXXXXX"
		var sumstr="["+currTd.style.paddingLeft+"]";
		//sumstr="["+currPaddingWidth+"]";
		var firstWz=currTd.style.paddingLeft
        while ( isChecked == true && j > 0 ) {

            currObj = document.all( j );
            currTd = document.all( j - 1 );
            preObj = document.all( j - 4 );
            preTd = document.all( j - 5 );

            prePaddingWidth = preTd.style.paddingLeft;			 
            currPaddingWidth = currTd.style.paddingLeft;
          
            if (  prePaddingWidth < currPaddingWidth ) {			   
                if(sumstr.indexOf("["+prePaddingWidth+"]")==-1){                
				  if(firstWz>prePaddingWidth)
				   preObj.checked = true;
                   sumstr+="["+prePaddingWidth+"]"
				 } 
				 
            }

            if ( prePaddingWidth == "0px" ) {
                break;
            }

            j -= 4;
        }

    }
}

/**
检查同级的复选框是否有被选中的项
true:有被选中的项
false:全未被选中
*/
function _getSameLevleChecked( index ) {

    var bForward = false;
    var bBackward = false;

    var index = obj.sourceIndex;
    var isChecked = obj.checked;

    if (isChecked) {
        return isChecked;
    }
    
    var currObj = obj;
    var currTd = document.all( index - 1 );
    var paddingWidth = currTd.style.paddingLeft;

    var nextObj = document.all( index + 4 );
    var nextTd = document.all( index + 3 );
    var nextPaddingWidth;
    
    var k = index;
    while ( k > 0 ) {
        
        currObj = document.all( k );
        currTd = document.all( k - 1 );
        nextObj = document.all( k + 4 );
        nextTd = document.all( k + 3 );

        if ( nextObj.type.toUpperCase() == "HIDDEN" && nextObj.name == "_CheckboxTreeEnd" ) {
            break;
        }

        nextPaddingWidth = nextTd.style.paddingLeft;

        if ( nextPaddingWidth <= paddingWidth ) {
            break;
        }
        if(!nextObj.disabled)
            nextObj.checked = isChecked;
        k += 4;
    }

}
