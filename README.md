# Hypercube unfoldings
OpenSCAD files for generating hypercube unfoldings. 

Inspired by Matt Parkers video about {hypercube unfoldings}(https://youtu.be/Yq3P-LhlcQo) I wanted to 3D print some of them. 
Just as {whuts.org}(whuts.org) I chose the numbering Moritz Firsching used on his {page}(https://github.com/mo271/mo271.github.io/tree/master/mo/198722)

## Usage
Download OpenSCAD (https://openscad.org/) and then clone this repo and include {hypercube_unfolding.scad}(https://github.com/JohanSpaedtke/hypercube_unfoldings/blob/main/src/scad/hypercube_unfolding.scad) in your script.

This will generate the first unfolding (https://whuts.org/unfolding/1)
```
include <path/to/hypercube_unfolding.scad>

hypercube_unfolding();
```

The method takes three optional arguments

* `mf_nr` - This is the Moritz Firsching number for the unfolding you want
* `size` - The length of the edges of the individual cubes
* `chamfers` - You can either choose simply true or false OR you can specify the chamfer as a percentage of the size of the individual cubes

Once you have the model rendered in OpenSCAD you can just hit 
`File -> Export -> Export as STL` to get a nice printable STL file. 

Happy tiling :) ! 