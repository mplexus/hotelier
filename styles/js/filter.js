var visible = {
  'filter1': false,
  'filter2': false,
  'filter3': false,
  'filter4': false,
  'filter5': false
};

function filter(element){
  if ($(element).prop('checked')){
    $('.filter'+$(element).val()).show();
    visible['filter'+$(element).val()] = true;
  } else {
    $('.filter'+$(element).val()).hide();
    visible['filter'+$(element).val()] = false;
  }
  applyVisibility(((Object.values(visible)).filter(function(x){return (x == true);})).length == 0);
  return false;
}

function applyVisibility(force) {
  for (v in visible) {
    if (visible.hasOwnProperty(v)) {
      if (visible[v] == true || force == true){
        $("."+v).show();
      } else {
        $("."+v).hide();
      }
    }
  }
  var visibleItems = $('[class*="filter"]').filter(':visible');
  $('.visibleCount').text(visibleItems.length);
  return false;
}

window.onload = function() {
  applyVisibility(true);
}
