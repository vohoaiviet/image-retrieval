/*
 * ImageSegement.java
 *
 * Created on 31 March 2007, 16:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;
import java.util.*;

/**
  * It is a class used for image segmentation.
  * It has methods that can return the (x, y) of the largest image
  * segment and the (x, y) of the boundary of the largest image segment.
  *
  */
public class ImageSegment {

	/**
	 * It is a inner class of ImageSegment class. It is
	 * used for storing information of a group of consecutive pixels
	 * with same discrete color level. It is updated during scanning
	 * every line and it can be used to determine whether a group of
	 * pixels is coherent or not.
	 */
	private class Coherence {
		/** storing (x, y) of the image segment
		 */
		private Vector<Integer> xyArea;
			
		/** dicrete color level of the coherence
		 */
		private int color;
		/** number of pixel of the coherence
		 */
		private int pixelCount;
		/** a flag check whether the coherece extends
		 */
		private boolean extend;

		/** flags check whether the color segment touch the edge or not
		*/
		private boolean[] edge;		// 0 => top, 1 => bottom, 2 => left, 3 => right
									// false => not touch, true => touch
		/**
		 * Constructor
		 * initialize the fields.
		 */
		public Coherence(int color, int size, int x, int y) {
			xyArea = new Vector<Integer>();
			xyArea.add(new Integer(x));
			xyArea.add(new Integer(y));
			this.color = color;
			pixelCount = 1;
			extend = true;
			edge = new boolean[4];
			edge[0] = edge[1] = edge[2] = edge[3] = false;

		}

		/**
		 * get the color of this coherence
		 *
		 * @return color of this coherence
		 */
		public int getColor() {
			return color;
		}

		/**
		 * get the pixel count of this coherence
		 *
		 * @return pixel count of this coherence
		 */
		public int getCount() {
			return pixelCount;
		}
		
		/**
		 * get the xyArea Vector
		 *
		 * @return the xyArea Vector
		 */
		public Vector getXYAreaVector() {
			return xyArea;
		}

		/**
		 * get the edge
		 */
		public boolean getEdge(int i) {
			return edge[i];
		}

		/**
		 * set the edge
		 */
		public void setEdge(int i) {
			edge[i] = true;
		}

	   /**
		 * get whether this coherence extends after scanning a new line
		 *
		 * @return boolean value whether this coherence extend.
		 */
		public boolean checkExtend() {
			boolean temp = extend;
			extend = false;
			return temp;
		}
		
		/**
		 * get whether this coherence touches the 4 edges of image
		 * after scanning a new line
		 *
		 * @return boolean value whether this coherence touches the 4 edges of image.
		 */
		public boolean checkEdge() {
			if(edge[0] == edge[1] && edge[1] == edge[2] &&
			   edge[2] == edge[3] && edge[3] == true)
				return true;
			else
				return false;
		}

		/**
		 * increase the pixel count by one
		 */
		public void increment(int x, int y) {
			pixelCount++;
			xyArea.add(new Integer(x));
			xyArea.add(new Integer(y));			
			extend = true;
		}

		/**
		 * merge another coherence into this by summing pixel count
		 *
		 * @param co second coherence being merged into current one
		 */
		public void merge(Coherence co) {
			pixelCount += co.getCount();
			Vector temp = co.getXYAreaVector();
			for(int i=0 ; i<temp.size() ; i++){
				xyArea.add((Integer)temp.elementAt(i));
			}
			for(int i=0 ; i<4 ; i++)
				edge[i] = edge[i] || co.getEdge(i);
			extend = true;
		}
	}


	/**
	 * Constructor
	 * It is the constructor. It does nothing.
	 */
	public ImageSegment() {
	};

	/**
	 * It gets the (x, y) of the largest image segment in no special order.
	 *
	 * @param image It is a DiscovirImage.
	 * @return Vector It stores the (x, y) of the largest image segment.
	 */
	public Vector getXYAreaVector(int [] blurpixels, int height, int width) {
		Vector colorCoherenceVector = new Vector(128);
		Vector<ImageSegment.Coherence> coherenceList = new Vector<ImageSegment.Coherence>();
		int size = width*height;

		// Get the blured image
		//image.blur();
                
		//int[] blurpixels = image.getPackedRGBPixel();

		// about 5% of the whole image is defined to be coherent
		final int threshold = size*5/100;

		int i, x, y;
		boolean leftExtend, rightExtend;
		int[] coherentHistogram = new int[64];
		int[] incoherentHistogram = new int[64];

		Coherence temp;
		Coherence[] relationNew, relationOld = null;

		// clear the array for storing number of
		// coherent and incoherent pixels
		for(i=0 ; i<64 ; i++){
			coherentHistogram[i] = incoherentHistogram[i] = 0;
		}

		// Discretize the pixels to 64 RGB levels (RRGGBB in binary)
		for (i=0 ; i<size ; i++)
			blurpixels[i] = (blurpixels[i]>>18&0x30) | (blurpixels[i]>>12&0xc) | (blurpixels[i]>>6&0x3);

		// Scan the first line and classify pixels to its group(Coherence)
		relationNew = new Coherence[width];
		for (x=0; x<width; x++)
			if ((x!=0) && (blurpixels[x] == blurpixels[x-1])) { // left
				relationNew[x] = relationNew[x-1];
				relationNew[x].increment(x, 0);
				relationNew[x].setEdge(0);
			}
			else { // new color
				relationNew[x] = new Coherence(blurpixels[x], size, x, 0);
				coherenceList.add(relationNew[x]);
				relationNew[x].setEdge(0);
			}
		evaluateLine(coherenceList);

		// Classify pixels to group(Coherence) line by line
		for (y=1; y<height; y++) {
			relationOld = relationNew;
			relationNew = new Coherence[width];
			for (x=0; x<width; x++) {
				if (blurpixels[y*width+x] == blurpixels[(y-1)*width+x]) { // up
					relationNew[x] = relationOld[x];
					relationNew[x].increment(x, y);
				}
				else {
					leftExtend = false;
					rightExtend = false;
					if (x!=0) {
						if (blurpixels[y*width+x] == blurpixels[(y-1)*width+x-1]) { // up left
							relationNew[x] = relationOld[x-1];
							relationNew[x].increment(x, y);
							leftExtend = true;
						}
						else if (blurpixels[y*width+x] == blurpixels[y*width+x-1]) { // left
							relationNew[x] = relationNew[x-1];
							relationNew[x].increment(x, y);
							leftExtend = true;
						}
					}
					if ((x!=width-1) && (blurpixels[y*width+x] == blurpixels[(y-1)*width+x+1])) { // up right
						if (!leftExtend) { // only up right
							relationNew[x] = relationOld[x+1];
							relationNew[x].increment(x, y);
						}
						else if (relationNew[x] != relationOld[x+1]) { // merge 2 coherence
							temp = relationOld[x+1];
							relationNew[x].merge(temp);
							// reassign relative relation
							for (i=0; i<width; i++)
								if (relationOld[i] == temp)
									relationOld[i] = relationNew[x];
							for (i=0; i<x; i++)
								if (relationNew[i] == temp)
									relationNew[i] = relationNew[x];
							coherenceList.remove(temp);
						}
						rightExtend = true;
					}
					if (!leftExtend && !rightExtend) { // new color
						relationNew[x] = new Coherence(blurpixels[y*width+x], size, x, y);
						coherenceList.add(relationNew[x]);
					}
				}
				if(x == 0)
					relationNew[x].setEdge(2);
				else if(x == (width-1))
					relationNew[x].setEdge(3);

				if(y == (height-1))
					relationNew[x].setEdge(1);
			}
			evaluateLine(coherenceList);
		}
		evaluateLine(coherenceList);

		int maxsize = 0;
		int maxgroup = -1;
		Coherence tmp;
		for(i=0 ; i<coherenceList.size() ; i++) {
			tmp = (Coherence)coherenceList.elementAt(i);
			if(tmp.getCount() > maxsize){
				maxsize = tmp.getCount();
				maxgroup = i;
			}
		}

		return ((Coherence)(coherenceList.elementAt(maxgroup))).getXYAreaVector();
	}

	/**
	 * It evaluate the effect after scanning a new line. It looks
	 * for any coherent group of pixels that does not extend to
	 * the new line and determine whether they should be coherent
	 * or incoherent by comparing the pixel count to threshold.
	 */
	private void evaluateLine(Vector coherenceList) {
		int color, i, k;
		Coherence item;
		int maxsize = 0;
		int maxgroup = -1;
		
		for (i=0; i<coherenceList.size(); i++) {
			item = (Coherence)coherenceList.elementAt(i);		
			if(item.checkEdge() == true){
				coherenceList.removeElementAt(i);
				i--;
			}
			else{
				if (item.getCount() > maxsize && item.getEdge(0)==false){
					maxsize = item.getCount();
					maxgroup = i;
				}
				
				if (!item.checkExtend() && item.getCount() < maxsize){
					coherenceList.removeElementAt(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * It gets the (x, y) of the boundary of the largest image segment in clockwise direction.
	 *
	 * @param image It is a DiscovirImage.
	 * @return Vector It stores the (x, y) of the boundary of the largest image segment.
	 */
	public Vector getXYBoundaryVector(int [] inImg, int height, int width) {
		Vector area = getXYAreaVector(inImg, height,width);
		int size = width*height;
		int[] result = new int[size];
		int i=0, j=0;
		int up = 0, down = 0, left = 0, right = 0;
		int upleft = 0, upright = 0, downleft = 0, downright = 0;

		for(i=0 ; i<size ; i++)
			result[i] = 0;

		for(i=0 ; i<area.size() ; i+=2){
			j = ((Integer)area.elementAt(i)).intValue()
				+ ((Integer)area.elementAt(i+1)).intValue()*width;
			result[j] = -1;
		}

		int boundarySize = 0;
		for(i=0 ; i<size ; i++){
			if(result[i]==-1 && isBoundary(i, result, size, width, height)){
				result[i] = -2;
				boundarySize++;
			}
		}

		return findBoundary(result, width, height, boundarySize);
	}
	
	/**
	 * It gets the (x, y) of the boundary of the largest image segment in clockwise direction.
	 *
	 * @param result It stores int representing the pixel is boundary or not.
	 * @param w It is the width of the image.
	 * @param h It is the height of the image.
	 * @param boundarySize It is the size of the image boindary.
	 * @return Vector It stores the (x, y) of the boundary of the largest image segment.
	 */
	private Vector findBoundary(int[] result, int w, int h, int boundarySize){
		int i, last, cx, cy, j=0, k=0;
		int size=w*h;
		int up = 0, down = 0, left = 0, right = 0;
		boolean next = false;
	
		int[][] boundary = new int[2][boundarySize];

		for(i=0 ; i<boundarySize ; i++){
			boundary[0][i] = boundary[1][i] = -1;
		}
		
		for(i=0 ; i<size && result[i]!=-2 ; i++);
		int start = i;
		
		do{
			boundary[0][j] = i%w;
			boundary[1][j] = i/w;
			
			up = i-w;
			down = i+w;
			left = i-1;
			right = i+1;
						
			if(up >= 0 && result[up]==-2 && next==false){
				i = up;
				next = true;
			}

			if((i%w) != (w-1)  && right<size && result[right]==-2 && next==false){
				i = right;
				next = true;
			}
			
			if(down < size  && result[down]==-2 && next==false){
				i = down;
				next = true;
			}
	
			if((i%w) != 0  && left>=0 && result[left]==-2 && next==false){
				i = left;
				next = true;
			}			
			
			if(next==false){
				last = j-1;
				cx = i%w;
				cy = i/w;
				if(boundary[0][last] < cx && boundary[1][last] == cy){	//moving right
					for(k=i+w-1 ; result[k]!=-2 ; k--);
					i=k;
				}
				else if(boundary[0][last] > cx && boundary[1][last] == cy){	//moving left
					for(k=i-w+1 ; result[k]!=-2 ; k++);
					i=k;
				}
				else if(boundary[0][last]==cx && boundary[1][last] > cy){	//moving up
					for(k=i+1+w ; result[k]!=-2 ; k+=w);
					i=k;
				}
				else if(boundary[0][last]==cx && boundary[1][last] < cy){	//moving down
					for(k=i-1-w ; result[k]!=-2 ; k-=w);
					i=k;
				}
				else{
					if(result[i-w-1] == -2)
						i=i-w-1;
					else if(result[i-w+1] == -2)
						i=i-w+1;
					else if(result[i+w-1] == -2)
						i=i+w-1;
					else if(result[i+w+1] == -2)
						i=i+w+1;
					else
						System.out.println("Error: boundary not moving! i = " + i + "  cx = " + cx + "  cy = " + cy);
				}
			}
			
			result[i] = -3;
			next=false;
			j++;
		}while(i!=start);

		Vector<Integer> xyBoundary = new Vector<Integer>();
		for(i=0 ; i<boundarySize && boundary[0][i]!=-1 ; i++){
			xyBoundary.add(new Integer(boundary[0][i]));
			xyBoundary.add(new Integer(boundary[1][i]));
		}

		return xyBoundary;			
	}
	
	/**
	 * It tells whether the i-th pixel is the boundary of the largest image segment or not.
	 *
	 * @param i It is the i-th pixel.
	 * @param result It stores int representing the pixel is boundary or not.
	 * @param size It is the size of the image.
	 * @param w It is the width of the image.
	 * @param h It is the height of the image.
	 * @return boolean It tells whether the i-th pixel is the boundary of the largest
	 				   image segment or not, true means yes, false means no.
	 */
	private boolean isBoundary(int i, int[] result, int size, int w, int h){
		int up = i-w, down = i+w, left = i-1, right = i+1;
		int upleft = i-w-1, upright = i-w+1, downleft = i+w-1, downright = i+w+1;
		boolean reply = false;

		if((up >= 0 && result[up]==0) || up<0){
			reply = true;
		}

		if((down < size  && result[down]==0) || down>=size){
			reply = true;
		}
	
		if(((i%w) != 0  && result[left]==0) || (i%w)==0){
			reply = true;
		}
	
		if(((i%w) != (w-1)  && result[right]==0) || (i%w)==(w-1)){
			reply = true;
		}
	
		if((up > 0 && (i%w) != 0  && (result[upleft]==0)) || up<0) {
			reply = true;
		}
	
		if((up > 0 && (i%w) != (w-1)  && (result[upright]==0)) || up<0){
			reply = true;
		}
			
		if((down < size && (i%w) != 0  && (result[downleft]==0)) || down>=size){
			reply = true;
		}
	
		if((down < size && (i%w) != (w-1)  && (result[downright]==0)) || down>=size){
			reply = true;
		}
		
		return reply;
	}
}
