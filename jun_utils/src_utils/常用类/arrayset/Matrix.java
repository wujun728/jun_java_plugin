package book.arrayset;

import java.text.DecimalFormat;

/**
 * 矩阵，使用二维数组
 */
public class Matrix implements Cloneable {
	/** 矩阵的数据 */
	private double[][] matrixData;
	/**
	 * 默认构造函数
	 */
	public Matrix() {
		this.init();
	}
	/**
	 * 用二维数组初始化矩阵对象
	 * @param data
	 */
	public Matrix(double[][] data) {
		if (!this.canConvert2Matrix(data)) {
			this.init();
		} else {
			// 把data的数据拷贝给矩阵
			// 矩阵的行数为data.length，列数为data[0].length
			this.matrixData = this.cloneArray(data);
		}
	}
	/**
	 * 矩阵加法运算。 
	 * 矩阵A和B可加的条件是矩阵A的行数等于矩阵B的行数，A的列数等于B的列数
	 * c[i][j] = a[i][j] + b[i][j]
	 * @param b  加数
	 * @return
	 */
	public Matrix add(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if ((this.matrixData.length != bData.length)
				|| (this.matrixData[0].length != bData[0].length)) {
			System.out.println("两个矩阵的大小不一致，不能完成加法运算");
			return c;
		}
		// 结果矩阵的数据
		double[][] cData = new double[this.matrixData.length][this.matrixData[0].length];
		for (int i = 0; i < this.matrixData.length; i++) {
			for (int j = 0; j < this.matrixData[0].length; j++) {
				// 两矩阵对应位置的数字做加法
				cData[i][j] = this.matrixData[i][j] + bData[i][j];
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * 矩阵减法运算。 
	 * 矩阵A和B可减的条件是矩阵A的行数等于矩阵B的行数，A的列数等于B的列数
	 * c[i][j] = a[i][j] + b[i][j]
	 * @param b 减数
	 * @return
	 */
	public Matrix sub(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if ((this.matrixData.length != bData.length)
				|| (this.matrixData[0].length != bData[0].length)) {
			System.out.println("两个矩阵的大小不一致，不能完成减法运算");
			return c;
		}
		// 结果矩阵的数据
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				// 两矩阵对应位置的数字做减法
				cData[i][j] = this.matrixData[i][j] - bData[i][j];
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * 矩阵的数乘，结果矩阵行列数不变
	 * c[i][j] = num * a[i][j]
	 * @param num
	 * @return
	 */
	public Matrix multiplyNum(double num) {
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = num * this.matrixData[i][j];
			}
		}
		return new Matrix(cData);
	}
	/**
	 * 矩阵乘法运算。
	 * 矩阵A和B可乘的条件是矩阵A的列数等于矩阵B的行数。
	 * 若A是一个p×q的矩阵，B是一个q×r的矩阵，则其乘积C=AB是一个p×r的矩阵
	 * C[i][j] = ("A[i][k] * B[k][j]"的累加)
	 * @param b  乘数
	 * @return
	 */
	public Matrix multiply(Matrix b) {
		if (b == null) {
			return null;
		}

		Matrix c = null;
		double[][] bData = b.getMatrixData();
		if (this.matrixData[0].length != bData.length) {
			System.out.println("做乘法时矩阵a的列数要与b的行数相等！");
			return c;
		}
		// 结果矩阵的数据，结果矩阵的行数为a的行数，列数为b的列数
		int cRow = this.matrixData.length;
		int cColumn = bData[0].length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = 0;
				for (int k = 0; k < this.matrixData[0].length; k++) {
					cData[i][j] += this.matrixData[i][k] * bData[k][j];
				}
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * 矩阵除法运算 
	 * A/B等价于A乘以B的逆矩阵
	 * @param b
	 * @return
	 */
	public Matrix divide(Matrix b) {
		if (b == null) {
			return null;
		}
		if (!this.isSquareMatrix() || (!b.isSquareMatrix())
				|| (this.matrixData.length != b.getMatrixData().length)) {
			System.out.println("矩阵的除法要求两个矩阵都是方阵，而且行数相等！");
			return null;
		}
		// 返回本矩阵与b的逆矩阵的乘积
		return this.multiply(b.inverseMatrix());
	}
	/**
	 * 求矩阵的逆矩阵
	 * 为矩阵右加一个单位矩阵后进行初等行变换，当左边变成单位矩阵时，右边就是求得的逆矩阵。
	 * 矩阵的初等行变换法则
	 * （1）交换变换：交换两行
	 * （2）倍法变换：给一行数据乘以一个非0常数
	 * （3）消法变换：把一行所有元素的k倍加到另一行的对应元素上去
	 * 将上述规则中的行换成列同样有效
	 * 只有方阵才可能有逆矩阵！
	 * @return
	 */
	public Matrix inverseMatrix() {
		if (!this.isSquareMatrix()) {
			System.out.println("不是方阵没有逆矩阵！");
			return null;
		}
		// 先在右边加上一个单位矩阵。
		Matrix tempM = this.appendUnitMatrix();
		// 再进行初等变换，把左边部分变成单位矩阵
		double[][] tempData = tempM.getMatrixData();
		int tempRow = tempData.length;
		int tempCol = tempData[0].length;
		//对角线上数字为0时，用于交换的行号
		int line = 0;
		//对角线上数字的大小
		double bs = 0;
		//一个临时变量，用于交换数字时做中间结果用
		double swap = 0;
		for (int i = 0; i < tempRow; i++) {
			// 将左边部分对角线上的数据等于0，与其他行进行交换
			if (tempData[i][i] == 0) {
				if (++line >= tempRow) {
					System.out.println("此矩阵没有逆矩阵！");
					return null;
				}

				for (int j = 0; j < tempCol; j++) {
					swap = tempData[i][j];
					tempData[i][j] = tempData[line][j];
					tempData[line][j] = swap;
				}

				//当前行（第i行）与第line行进行交换后，需要重新对第i行进行处理
				//因此，需要将行标i减1，因为在for循环中会将i加1。
				i--;
				//继续第i行处理，此时第i行的数据是原来第line行的数据。
				continue;
			}

			//将左边部分矩阵对角线上的数据变成1.0
			if (tempData[i][i] != 1) {
				bs = tempData[i][i];
				for (int j = tempCol - 1; j >= 0; j--) {
					tempData[i][j] /= bs;
				}
				//将左边部分矩阵变成上对角矩阵，
				//所谓上对角矩阵是矩阵的左下角元素全为0
				for (int iNow = i + 1; iNow < tempRow; iNow++) {
					for (int j = tempCol - 1; j >= i; j--) {
						tempData[iNow][j] -= tempData[i][j] * tempData[iNow][i];
					}
				}
			}
		}

		//将左边部分矩阵从上对角矩阵变成单位矩阵，即将矩阵的右上角元素也变为0
		for (int i = 0; i < tempRow - 1; i++) {
			for (int iNow = i; iNow < tempRow - 1; iNow++) {
				for (int j = tempCol - 1; j >= 0; j--) {
					tempData[i][j] -= tempData[i][iNow + 1]
							* tempData[iNow + 1][j];
				}
			}
		}

		// 右边部分就是它的逆矩阵
		Matrix c = null;
		int cRow = tempRow;
		int cColumn = tempCol / 2;
		double[][] cData = new double[cRow][cColumn];
		// 将右边部分的值赋给cData
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = tempData[i][cColumn + j];
			}
		}
		// 得到逆矩阵，返回
		c = new Matrix(cData);
		return c;
	}
	/**
	 * 转置矩阵，转置矩阵的行数等于原矩阵的列数，列数等于原矩阵的行数
	 * c[i][j] = a[j][i]
	 * 
	 * @return
	 */
	public Matrix transposeMatrix() {
		int cRow = this.matrixData[0].length;
		int cColumn = this.matrixData.length;
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				cData[i][j] = this.matrixData[j][i];
			}
		}
		return new Matrix(cData);
	}
	/**
	 * 判断矩阵是否为方阵
	 * @return
	 */
	public boolean isSquareMatrix() {
		if (this.matrixData.length == this.matrixData[0].length) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @return 返回 matrixData的值。
	 */
	public double[][] getMatrixData() {
		// 用户可能会修改通过调用此方法得到的数组，而数组是对象，
		// 用户的修改会影响到本矩阵的数据，因此，
		// 这里返回的是本矩阵数据的克隆而不是引用，以防止修改本矩阵的数据。
		return cloneArray(this.matrixData);
	}
	/**
	 * 设置矩阵的matrixData值
	 * @param data  当前数据
	 */
	public void setMatrixData(double[][] data) {
		if (this.canConvert2Matrix(data)){
			//与getMatrixData方法一样，这里是将当前数据的克隆一份，赋给本矩阵
			//以防止用户修改当前数据，导致本矩阵的数据也发生变化
			this.matrixData = this.cloneArray(data);
		}
	}
	/**
	 * 矩阵的字符串形式
	 */
	public String toString(){
		//把矩阵的数据换成字符串返回
		return this.arrayToString(this.matrixData);
	}
	/**
	 * 以矩阵的形式输出
	 */
	public void display() {
		System.out.println(this.toString());
	}
	/**
	 * 比较矩阵是否相等，覆盖了Object类的equals方法
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Matrix) {
			Matrix objM = (Matrix) obj;
			double[][] objMData = objM.getMatrixData();
			for (int i = 0; i < objMData.length; i++) {
				// 一行一行的比较
				for (int j = 0; j < objMData[0].length; j++) {
					// 相应位置的数字做比较，如果不等，就返回false
					if (this.matrixData[i][j] != objMData[i][j]) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 获取矩阵的hashCode，覆盖了Object类的hashCode方法
	 */
	public int hashCode() {
		// 使用矩阵的字符串形式的hashCode做为矩阵的hashCode
		return this.toString().hashCode();
	}
	/**
	 * 克隆一个矩阵，覆盖了Object类的clone方法
	 */
	public Object clone() {
		try {
			Matrix matrix = (Matrix) super.clone();
			matrix.setMatrixData(this.matrixData);
			return matrix;
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}
	/**
	 * 用单位矩阵初始化
	 */
	private void init() {
		this.matrixData = new double[][] { { 1.0, 0.0, 0.0 },
				{ 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0 } };
	}
	/**
	 * 判断一个二维数组能够转换成矩阵 
	 * 矩阵要求数组的第二维长度必须一样。
	 * @param data
	 * @return
	 */
	private boolean canConvert2Matrix(double[][] data) {
		if (data == null) {
			return false;
		}
		// 逐个比较，如果有长度不等的，便返回false
		for (int i = 0; i < data.length - 1; i++) {
			if (data[i].length != data[i + 1].length) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 克隆一个二维数组
	 * @param src	源二维数组
	 * @return
	 */
	private double[][] cloneArray(double[][] src) {
		if (src == null) {
			return null;
		}
		// 使用数组的克隆功能
		return (double[][]) src.clone();
	}
	/**
	 * 给矩阵右边加上一个单位矩阵
	 * @return
	 */
	private Matrix appendUnitMatrix() {
		Matrix c = null;
		int cRow = this.matrixData.length;
		int cColumn = this.matrixData[0].length * 2;
		// 结果矩阵的行数等于现有矩阵，列数是现有矩阵的两倍。
		double[][] cData = new double[cRow][cColumn];
		for (int i = 0; i < cRow; i++) {
			for (int j = 0; j < cColumn; j++) {
				if (j < this.matrixData[0].length) {
					cData[i][j] = this.matrixData[i][j];
				} else {
					if ((j - i) == this.matrixData[0].length) {
						// 右边的单位矩阵是对角线上值为1，其他位置为0
						cData[i][j] = 1.0;
					} else {
						cData[i][j] = 0;
					}
				}
			}
		}
		c = new Matrix(cData);
		return c;
	}
	/**
	 * 二维数组的字符串形式
	 * @param array
	 * @return
	 */
	private String arrayToString(double[][] array) {
		//对数据进行格式化，这里只保留2位小数
		DecimalFormat df = new DecimalFormat("0.00");

		//使用StringBuffer而不是String，为了避免新建太多的String对象
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				sb.append(df.format(array[i][j])).append(" ");
			}
			// 输出完一行数据就换行
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// 默认构造函数
		Matrix defaultM = new Matrix();
		System.out.println("默认矩阵:");
		defaultM.display();
		// 带参数的构造函数
		// 逐个逐个的初始化二维数组
		double[][] data0 = new double[3][3];
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				data0[i][j] = i*3 + j;
			}
		}
		Matrix m0 = new Matrix(data0);
		System.out.println("矩阵m0:");
		m0.display();
		// 用初始化的方式构造一个二维数组
		double[][] data1 = new double[][] { { 4.0, 5.0, 3.0 },
				{ 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };

		Matrix m1 = new Matrix(data1);
		System.out.println("矩阵m1:");
		m1.display();
		//判断矩阵是否为方阵
		System.out.println("矩阵m0是方阵？ " + m0.isSquareMatrix());
		System.out.println("矩阵m1是方阵？ " + m1.isSquareMatrix());
		System.out.println();
		// 矩阵加法
		System.out.println("矩阵m0+m1的结果:");
		m0.add(m1).display();
		// 矩阵减法
		System.out.println("矩阵m0-m1的结果:");
		m0.sub(m1).display();
		//矩阵数乘
		System.out.println("矩阵m0*2的结果:");
		m0.multiplyNum(2).display();
		// 矩阵乘法
		System.out.println("矩阵m0*m1的结果:");
		Matrix mTemp = m0.multiply(m1);
		if (mTemp != null) {
			mTemp.display();
		} else {
			System.out.println("矩阵m0和m1不能做乘法运算！");
		}
		
		// 获取矩阵的转置矩阵
		System.out.println("矩阵m0的转置矩阵:");
		m0.transposeMatrix().display();
		// 获取矩阵的逆矩阵
		System.out.println("矩阵m1的逆矩阵:");
		mTemp = m1.inverseMatrix();
		if (mTemp != null) {
			mTemp.display();
			//矩阵与它的逆矩阵的乘积
			System.out.println("矩阵m1与它的逆矩阵的乘积:");
			m1.multiply(mTemp).display();
		} else {
			System.out.println("矩阵m0没有逆矩阵！");
		}
		// 矩阵除法
		System.out.println("矩阵m0/m1的结果:");
		mTemp = m0.divide(m1);
		if (mTemp != null) {
			mTemp.display();
		} else {
			System.out.println("矩阵m0和m1不能做除法运算！");
		}
		
		// 矩阵克隆
		System.out.println("根据m0克隆的m2:");
		Matrix m2 = (Matrix) m1.clone();
		m2.display();
		// 比较矩阵大小
		System.out.println("m1.equals(m2) = " + m1.equals(m2));
		//获取矩阵的hashCode
		System.out.println("m1的hashCode: " + m1.hashCode());
		System.out.println("m2的hashCode: " + m2.hashCode());
	}
}
