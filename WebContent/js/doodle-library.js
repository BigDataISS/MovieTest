
/* Doodle Drawing Library
 *
 * Drawable and Primitive are base classes and have been implemented for you.
 * Do not modify them! 
 *
 * Stubs have been added to indicate where you need to complete the
 * implementation.
 * Please email me if you find any errors!
 */

/*
 * Root container for all drawable elements.
 */
function Doodle (context) {
    this.context = context;
    this.children = [];
}

Doodle.prototype.draw = function() {
 	// Your draw code here
    for (var i = 0; i < this.children.length; i++) {
        if (this.children[i].visible) {
            this.children[i].draw(this.context);
        }
    }
};

/* Base class for all drawable objects.
 * Do not modify this class!
 */
function Drawable (attrs) {
    var dflt = { 
        left: 0,
        top: 0,
        visible: true,
        theta: 0,
        scale: 1
    };
    attrs = mergeWithDefault(attrs, dflt);
    this.left = attrs.left;
    this.top = attrs.top;
    this.visible = attrs.visible;
    this.theta = attrs.theta*Math.PI/180;
    this.scale = attrs.scale;
}

Drawable.prototype.getWidth = function(context) {
  console.log("ERROR: Calling unimplemented draw method on drawable object.");
  return 0;
}
Drawable.prototype.getHeight = function(context) {
  console.log("ERROR: Calling unimplemented draw method on drawable object.");
  return 0;
}

Drawable.prototype.draw = function(context) {
    console.log("ERROR: Calling unimplemented draw method on drawable object.");
};


/* Base class for objects that cannot contain child objects.
 * Do not modify this class!
 */
function Primitive(attrs) {
    var dflt = {
        lineWidth: 1,
        color: "black"
    };
    attrs = mergeWithDefault(attrs, dflt);
    Drawable.call(this, attrs);
    this.lineWidth = attrs.lineWidth;
    this.color = attrs.color;
}
Primitive.inheritsFrom(Drawable);

function Text(attrs) {
    var dflt = {
        content: "",
        fill: "black", //color
        font: "Helvetica", //font family
        size: 12, //Size in pt
        bold: false //bold boolean
    };
    attrs = mergeWithDefault(attrs, dflt);
    Drawable.call(this, attrs);
  
    //Rest of constructor code here
	this.content = attrs.content;
	this.fill = attrs.fill;
	this.font = attrs.font;
	this.size = attrs.size;
	this.bold = attrs.bold;
	
	this.left = attrs.left;
    this.top = attrs.top;
}
Text.inheritsFrom(Drawable);

//Text methods here
Text.prototype.getWidth = function(context) {
  var si = MeasureText(this.content, this.bold, this.font, this.size);
  return si[0];
}
Text.prototype.getHeight = function(context) {
  var si = MeasureText(this.content, this.bold, this.font, this.size);
  return si[1];
}

Text.prototype.draw = function(context) {
	context.beginPath();
	var str = "" + this.size + "pt " + this.font;
	if(this.bold){
		str += " bold";
	}
    context.font = str;
	context.fillStyle = this.fill;
	context.fillText(this.content, this.left, this.top);
	
};


function DoodleImage(attrs) {
    var dflt = {
        width: -1,
        height: -1,
        src: "",
    };
    attrs = mergeWithDefault(attrs, dflt);
    Drawable.call(this, attrs);
 
    //Rest of constructor code here
	this.width = attrs.width;
	this.height = attrs.height;
	this.src = attrs.src;
	
	this.top = attrs.top;
	this.left = attrs.left;
}
DoodleImage.inheritsFrom(Drawable);

//DoodleImage methods here
DoodleImage.prototype.getWidth = function(context) {
  return this.width;
}
DoodleImage.prototype.getHeight = function(context) {
  return this.height;
}

DoodleImage.prototype.draw = function (context) {
    // draw code here

    var x = this.left;
    var y = this.top;
    var width = this.width;
    var height = this.height;

    var imageObject = new Image();
    imageObject.src = this.src;

    imageObject.onload = function() {
        if (width === -1) {
            width = this.width;
        }
        if (height === -1) {
            height = this.height;
        }
		
        context.drawImage(imageObject, x, y, width, height);
    };
};

function Line(attrs) {
    var dflt = {
        startX: 0,
        startY: 0,
        endX: 0,
        endY: 0
    };
    attrs = mergeWithDefault(attrs, dflt);
    Primitive.call(this, attrs);
    //Rest of constructor code here
	this.startX = attrs.startX;
	this.startY = attrs.startY;
	this.endX = attrs.endX;
	this.endY = attrs.endY;
	
	this.color = attrs.color;
    this.lineWidth = attrs.lineWidth;
}
Line.inheritsFrom(Primitive);

//Line methods here
Line.prototype.getWidth = function(context) {
  return Math.abs(this.startX-this.endX);
}
Line.prototype.getHeight = function(context) {
  return Math.abs(this.startY-this.endY);
}

Line.prototype.draw = function (context) {
    // your draw code here
    var x1 = parseFloat(this.startX);
    context.beginPath();
    context.strokeStyle = this.color;
    context.lineWidth = this.lineWidth;
    context.moveTo(this.startX, this.startY);
    context.lineTo(this.endX, this.endY);
    context.stroke();

};

function Rectangle(attrs) {
    var dflt = {
        x: 0,
        y: 0,
        width: 0,
        height: 0,
    };
    attrs = mergeWithDefault(attrs, dflt);
    Primitive.call(this, attrs);
    //Rest of constructor code here
	this.x = attrs.left;
	this.y = attrs.top;
	this.width = attrs.width;
	this.height = attrs.height;
	
	this.color = attrs.color;
}
Rectangle.inheritsFrom(Primitive);

//Rectangle Methods here
Rectangle.prototype.getWidth = function(context) {
  return this.width;
}
Rectangle.prototype.getHeight = function(context) {
  return this.height;
}

Rectangle.prototype.draw = function (context) {
    // draw code here
    context.beginPath();
    context.lineWidth = this.lineWidth;
    context.strokeStyle = this.color;
    context.rect(this.x, this.y, this.width, this.height);
    context.stroke();
};

function Container(attrs) {
    var dflt = {
        width: 100,
        height: 100,
        fill: false,
        borderColor: "black",
        borderWidth: 0,
    };
    attrs = mergeWithDefault(attrs, dflt);
    Drawable.call(this, attrs);    
    //Rest of constructor code here
	this.width = attrs.width;
	this.height = attrs.height;
	this.fill = attrs.fill;
	this.borderColor = attrs.borderColor;
	this.borderWidth = attrs.borderWidth;
	
	this.left = attrs.left;
	this.top = attrs.top;
	this.theta = attrs.theta;
	
	this.children = [];
}
Container.inheritsFrom(Drawable);

//Rest of container methods here
Container.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
Container.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
Container.prototype.layout = function(context) {
  console.log("ERROR: Calling unimplemented draw method on container object.");
  return 0;
}

Container.prototype.draw = function (context) {
    // draw code here
  
    context.beginPath();

    context.rect(this.left, this.top, this.width, this.height);
    if (this.borderWidth !== 0) {
        context.lineWidth = this.borderWidth;
        context.strokeStyle = this.borderColor;
        context.stroke();
    }
    if (this.fill !== false) {
        context.fillStyle = this.fill;
        context.fillRect(this.left, this.top, this.width, this.height);
    }
	context.closePath();

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();
        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				/*
				if(typeof(this.children[i].src) != "undefined"){
					this.children[i].left=this.left;
					this.children[i].top=this.top; 
				}
				*/
				this.children[i].draw(context);
            }
            context.restore();
        }
        context.restore();
    }
};

function Pile(attrs) {
  Container.call(this, attrs);   
  //Rest of constructor code here
}
Pile.inheritsFrom(Container);

//Rest of pile methods here
Pile.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
Pile.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
Pile.prototype.layout = function(context) {
  return "Pile";
}

Pile.prototype.draw = function (context) {
    // draw code here
  
    context.beginPath();

    context.rect(this.left, this.top, this.width, this.height);
    if (this.borderWidth !== 0) {
        context.lineWidth = this.borderWidth;
        context.strokeStyle = this.borderColor;
        context.stroke();
    }
    if (this.fill !== false) {
        context.fillStyle = this.fill;
        context.fillRect(this.left, this.top, this.width, this.height);
    }
    context.closePath();

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();
        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				this.children[i].left = this.left;
				this.children[i].top = this.top;
                this.children[i].draw(context);
            }
            context.restore();
        }
        context.restore();
    }
};

function Row(attrs) {
  Container.call(this, attrs);    
  //Rest of constructor code here
}
Row.inheritsFrom(Container);

//Rest of row methods here
Row.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
Row.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
Row.prototype.layout = function(context) {
  return "Row";
}

Row.prototype.draw = function (context) {
    // draw code here
  
    context.beginPath();

    context.rect(this.left, this.top, this.width, this.height);
    if (this.borderWidth !== 0) {
        context.lineWidth = this.borderWidth;
        context.strokeStyle = this.borderColor;
        context.stroke();
    }
    if (this.fill !== false) {
        context.fillStyle = this.fill;
        context.fillRect(this.left, this.top, this.width, this.height);
    }
    context.closePath();

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();
		var cellWidth = this.width/this.children.length;
		var sX = this.left;
		
        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				
				this.children[i].left = sX;
				this.children[i].top =(this.getHeight()-this.children[i].getHeight())/2;
                this.children[i].draw(context);
				
				if(this.children[i].width>cellWidth)
					sX += cellWidth;
				else
					sX += this.children[i].width+this.children[i].borderWidth;
            }
            context.restore();
        }
        context.restore();
    }
};

function Column(attrs) {
  Container.call(this, attrs);  
  //Rest of constructor code here
}
Column.inheritsFrom(Container);

//Rest of column methods here

Column.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
Column.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
Column.prototype.layout = function(context) {
  return "Column";
}

Column.prototype.draw = function (context) {
    // draw code here
  
    context.beginPath();

    context.rect(this.left, this.top, this.width, this.height);
    if (this.borderWidth !== 0) {
        context.lineWidth = this.borderWidth;
        context.strokeStyle = this.borderColor;
        context.stroke();
    }
    if (this.fill !== false) {
        context.fillStyle = this.fill;
        context.fillRect(this.left, this.top, this.width, this.height);
    }
    context.closePath();

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();
		var cellHeight = this.height/this.children.length;
		var sY = this.top;
		
        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				
				this.children[i].left = (this.getWidth()-this.children[i].getWidth())/2;
				this.children[i].top = sY;
				if(typeof(this.children[i].font) != "undefined")
					this.children[i].top = sY + this.children[i].getHeight()/2;
                this.children[i].draw(context);
				
				if(this.children[i].height>cellHeight)
					sY += cellHeight;
				else
					sY += this.children[i].height+this.children[i].borderWidth;
            }
            context.restore();
        }
        context.restore();
    }
};

function Circle(attrs) {
  Container.call(this, attrs);      
  var dflt = {
    layoutCenterX: this.width / 2,
    layoutCenterY: this.height / 2,
    layoutRadius: Math.min(this.width, this.height) / 2 - 30
  };
  attrs = mergeWithDefault(attrs, dflt);
  //Rest of constructor code here
  
	this.layoutCenterX = attrs.layoutCenterX;
	this.layoutCenterY = attrs.layoutCenterY;
	this.layoutRadius = attrs.layoutRadius;
}
Circle.inheritsFrom(Container);

//Rest of circle methods here
Circle.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
Circle.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
Circle.prototype.layout = function(context) {
  return "Circle";
}

Circle.prototype.draw = function (context) {
    // draw code here
    context.beginPath();
    context.rect(this.left, this.top, this.width, this.height);
    if (this.borderWidth !== 0) {
        context.lineWidth = this.borderWidth;
        context.strokeStyle = this.borderColor;
        context.stroke();
    }
    if (this.fill !== false) {
        context.fillStyle = this.fill;
        context.fillRect(this.left, this.top, this.width, this.height);
    }
    context.closePath();

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();

        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				this.children[i].left = 200 + Math.sin(i * Math.PI / 4) * 100;
                this.children[i].top = 200- Math.cos(i * Math.PI / 4) * 100;  
                this.children[i].draw(context);
            }
            context.restore();
        }
        context.restore();

    }
};


function OvalClip(attrs) {
	Container.call(this, attrs);
	//Rest of constructor code here
  
	this.children = []; 
    this.width = attrs.width;
    this.height = attrs.height;
    this.borderWidth = attrs.borderWidth;
    this.left = attrs.left;
    this.top = attrs.top;
}
OvalClip.inheritsFrom(Container);

//Rest of ovalClip methods here
OvalClip.prototype.getWidth = function(context) {
  return this.width + 2*this.borderWidth;
}
OvalClip.prototype.getHeight = function(context) {
  return this.height + 2*this.borderWidth;
}
OvalClip.prototype.layout = function(context) {
  return "OvalClip";
}
OvalClip.prototype.draw = function (context) {
    // draw code here
    context.beginPath();
	var x=this.left+this.width/2;
	var y=this.top+this.height/2;
	var a=this.width/2;
	var b=this.height/2;
    drawOval(context,x,y,a,b)

    //children draw code
    if (this.children.length > 0) {
        context.save();
        context.clip();
        for (var i = 0; i < this.children.length; i++) {
            context.save();
            if (this.children[i].visible) {
                context.translate(this.left, this.top);
                context.rotate(this.theta);
				this.children[i].left=this.left;
				this.children[i].top=this.top; 
                this.children[i].draw(context);
            }
            context.restore();
        }
	}
};
function drawOval(context, x, y, a, b)
{
   var step = (a > b) ? 1 / a : 1 / b;
   context.beginPath();
   context.moveTo(x + a, y); 
   for (var i = 0; i < 2 * Math.PI; i += step)
   {
      context.lineTo(x + a * Math.cos(i), y + b * Math.sin(i));
   }
   context.closePath();
   context.stroke();
};

/**
 * Measurement function to measure canvas fonts
 *
 * @return: Array with two values: the first [0] is the width and the seconds [1] is the height 
 *          of the font to be measured. 
 **/
function MeasureText(text, bold, font, size)
{
    // This global variable is used to cache repeated calls with the same arguments
    var str = text + ':' + bold + ':' + font + ':' + size;
    if (typeof(__measuretext_cache__) == 'object' && __measuretext_cache__[str]) {
        return __measuretext_cache__[str];
    }

    var div = document.createElement('DIV');
        div.innerHTML = text;
        div.style.position = 'absolute';
        div.style.top = '-100px';
        div.style.left = '-100px';
        div.style.fontFamily = font;
        div.style.fontWeight = bold ? 'bold' : 'normal';
        div.style.fontSize = size + 'pt';
    document.body.appendChild(div);
    
    var size = [div.offsetWidth, div.offsetHeight];

    document.body.removeChild(div);
    
    // Add the sizes to the cache as adding DOM elements is costly and can cause slow downs
    if (typeof(__measuretext_cache__) != 'object') {
        __measuretext_cache__ = [];
    }
    __measuretext_cache__[str] = size;
    
    return size;
}