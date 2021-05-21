include <chamfered_cube.scad>

module hypercube_unfolding(mf_nr = 1, size = 1, chamfers = true){
    index = mf_nr - 1;
    if(mf_nr > 0 && mf_nr <= 261){
        translate(unfoldings{index}{0}*size){
            __hu_cube(size, chamfers);
        }    
        translate(unfoldings{index}{1}*size){
            __hu_cube(size, chamfers);
        }
        translate(unfoldings{index}{2}*size){
            __hu_cube(size, chamfers);
        }
        translate(unfoldings{index}{3}*size){
            __hu_cube(size, chamfers);
        }
        translate(unfoldings{index}{4}*size){
            __hu_cube(size, chamfers);
        } 
        translate(unfoldings{index}{5}*size){
            __hu_cube(size, chamfers);
        }  
        translate(unfoldings{index}{6}*size){
            __hu_cube(size, chamfers);
        } 
        translate(unfoldings{index}{7}*size){
            __hu_cube(size, chamfers);
        }   
    }
    else{
        echo("Unknown hypercube unfolding");
    }
}

module __hu_cube(size = 1, chamfers){
    if(is_bool(chamfers)){ 
        if(chamfers){
            chamfered_cube(size, chamfer_percent=0.1);
        }else{
          cube(size);  
        }
    }else{
        chamfered_cube(size, chamfer_percent=chamfers);
    }
}