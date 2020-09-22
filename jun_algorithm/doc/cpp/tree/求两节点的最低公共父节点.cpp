
/*
输入二叉树的两个节点，输出这两个节点在树种最低的公共父节点
*/
/*
遍历二叉树，找到一条从根节点开始到目标节点的路径，然后
在两条路径上查找共同的父节点
*/

/*
得到从根到某一节点的路径
*/
bool GetNodePath(BinTree* root,BinTree* node,vector<BinTree*>& path)
{
	if(root == NULL)
		return false;
	if(root == node)
		return true;
	else if(GetNodePath(root->left,node,path))
	{
		path.push_back(root->left);
		return true;
	}
	else if(GetNodePath(root->right,node,path))
	{
		path,push_back(root->right);
		return true;
	}
	return false;
}


BinTree* GetLastNode(vector<BinTree*>& path1,vector<BinTree*>& path2)
{
	vector<BinTree*>::iterator iter1 = path1.begin();
	vector<BinTree*>::iterator iter2 = path2.begin();
	BinTree* plast;
	while(iter1 != path1.end() && iter2 != path2.end())
	{
		if(*iter1 == *iter2)
			plast = *iter1;
		else
			break;
		iter1++;
		iter2++;
	}
	return plast;
}

BinTree* GetParent(BinTree* root,BinTree* node1,BinTree* node2)
{
	if(root == NULL || node1 == NULL || node2 == NULL)
		return NULL;
	vector<BinTree*> path1;
	GetNodePath(root,node1,path1);

	vector<BinTree*> path2;
	GetNodePath(root,node2,path2);
	return GetLastNode(path1,path2);
}

/*
第二种方法
从头开始遍历，一旦发现有节点和两个节点中的一个相等，那么次节点就是目标
节点，要么公共父节点在左子树，要么在右子树。
如果发现两个节点一个在左子树，一个在右子树，那么当前节点就是公共父节点
如果发现都在右子树，那么公共父节点就在右子树，
如果发现都在左子树，那么公共父节点在右子树
*/
bool FindNode(BinTree* root,BinTree* node)
{
	if(root == NULL)
		return false;
	if(root == node)
		return true;
	return (FindNode(root->left,node) || FindNode(root->right,node));
}

BinTree* LCP(BinTree* root,BinTree* first,BinTree* second)
{
	if(root == first || root== second)
		return root;
	bool isLeft = false;
	isLeft = FindNode(root->left,first);
	if(isLeft)
	{
		if(FindNode(root->left,second))
			return LCP(root->left,first,second);
		else
			return root;
	}
	else
	{
		if(FindNode(root->right,second))
			return LCP(root->right,first,second);
		else
			return root;
	}
}

/*
另一种简洁版
如果在左子树查找到了节点，left != NULL,那么看一下right是否为NULL
如果right != NULL ,那么说明左右两个子树中都查找到了公共父节点
那么说明最低的公共父节点就是当前的节点
*/

BinTree* LCP2(BinTree8 root,BinTree* first,BinTree* second)
{
	if(root == first || root== second)
		return root;
	if(root == NULL)
		return NULL;
	BinTree* left = LCP2(root->left,first,second);
	BinTree* right = LCP2(root->right,first,second);
	if(left == NULL)
		return right;
	else if(right == NULL)
		return left;
	else
		return root;
}
