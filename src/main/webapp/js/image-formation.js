/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
for (var i = 0; i < document.images.length; i++) {
    var x = document.images[i]
    x.src = x.src.replace('https', 'http');
}