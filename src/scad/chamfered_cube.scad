
module __tilted_cube(size, chamfer_size){
    touching = sqrt(2)/4;
    chamfer_angle = 45;
    translate([0,-(touching*size)+chamfer_size/2,size-(touching*size)-chamfer_size/2]) {
        rotate(chamfer_angle,[1,0,0]) {
                cube(size);
        }
    }
}

module __chamfers(size, chamfer_size){
    __tilted_cube(size, chamfer_size);
    translate([0,size,0]){
        mirror([0,1,0]) {
            __tilted_cube(size, chamfer_size);
        }
    }
    translate([0,0,size]){
        mirror([0,0,1]) {
            __tilted_cube(size, chamfer_size);
        }
    }    
    translate([0,size,0]){
        mirror([0,1,0]) {
            translate([0,0,size]){
                mirror([0,0,1]) {
                    __tilted_cube(size, chamfer_size);
                }
            }   
        }    
    }    
}

function __chamfer_size(size, chamfer_percent, chamfer_size)=
    (is_undef(chamfer_size)
        ?(is_num(chamfer_percent)
            ?[chamfer_percent*size,chamfer_percent*size,chamfer_percent*size]
            :chamfer_percent*size)
        :(is_num(chamfer_size)
            ?[chamfer_size,chamfer_size,chamfer_size]
            :chamfer_size)
    );

module chamfered_cube(size = 1, chamfer_percent = 0.05, chamfer_size) {
    _chamfer_size = __chamfer_size(size, chamfer_percent, chamfer_size);
    difference() {
        cube(size);
        __chamfers(size, _chamfer_size.x);
        translate([size,0,0]){
            rotate(90, [0,0,1]){
                __chamfers(size, _chamfer_size.y);
            }
        }
        translate([0,0,size]){
            rotate(90, [0,1,0]){
                __chamfers(size, _chamfer_size.z);
            }
        }
    }
}