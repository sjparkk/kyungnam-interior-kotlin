$(document).ready(function() { 
    var myLatlng = new google.maps.LatLng(37.52321,126.90293); // 위치값 위도 경도
    var Y_point = 37.52321; // Y 좌표
    var X_point = 126.90293; // X 좌표
    var zoomLevel = 18; // 지도의 확대 레벨 : 숫자가 클수록 확대정도가 큼 
    var markerTitle = "경남인테리어"; // 현재 위치 마커에 마우스를 오버을때 나타나는 정보 
    var markerMaxWidth = 300; // 마커를 클릭했을때 나타나는 말풍선의 최대 크기 // 말풍선 내용 
    var contentString = '<div>' + '<p>서울 영등포구 양산로 177 '+ '<br>' +'경남아파트상가 108호 경남인테리어</p>' + '</div>';
    var myLatlng = new google.maps.LatLng(Y_point, X_point); 
    var mapOptions = { zoom: zoomLevel, center: myLatlng, mapTypeId: google.maps.MapTypeId.ROADMAP } 
    var map = new google.maps.Map(document.getElementById('map_ma'), mapOptions); 
    var marker = new google.maps.Marker({ position: myLatlng, map: map, title: markerTitle }); 
    var infowindow = new google.maps.InfoWindow( { content: contentString, maxWizzzdth: markerMaxWidth, shouldFocus: true } );
    infowindow.open(map, marker);
    google.maps.event.addListener(marker, 'click', function() { infowindow.open(map, marker); });
}); 