

$('.filterbox').click(function(){
  if ($(this).prop('checked')){
    alert($(this).value());
  }else{
    Console.log("not checked");
  }
});

var visible = {
  'filter1': true,
  'filter2': true,
  'filter3': true,
  'filter4': true,
  'filter5': true
};

function filter(element){
  if ($(element).prop('checked')){
    $('.filter'+$(element).val()).show();
    visible['filter'+$(element).val()] = true;
  } else {
    $('.filter'+$(element).val()).hide();
    visible['filter'+$(element).val()] = false;
  }
  for (v in visible) {
    if (visible.hasOwnProperty(v)) {
      if (visible.v == true){
        $(v).show();
      } else {
        $(v).hide();
      }
    }
  }
  return false;
}
