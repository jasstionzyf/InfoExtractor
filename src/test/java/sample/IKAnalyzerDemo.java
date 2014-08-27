/**
 * IK 中文分词  版本 5.0


 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package sample;

import java.io.File;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;



/**
 * IKAnalyzer 示例
 * 2012-3-2
 * 
 * 以下是结合Lucene3.4 API的写法
 *
 */
public class IKAnalyzerDemo {
	public static void main(String[] args){
		//Lucene Document的域名
		String fieldName = "text";
		 //检索内容
		String text="Goods [category=休闲包, categoryId=null, goodName=YESO户外大师时尚简约大翻盖单肩斜跨包2072-1黑, description=&;.bigapic{width:750px;height:auto;}【品牌】YESO【货号】Y2072-1【颜色】黑色【面料】牛津配皮【尺寸】高300mm宽260mm厚100mm.bigapic{width:750px;height:auto;}潮流本来就新旧交替，新旧融汇。YESO户外大师系列产品，自成立以来，在davidchen设计组的领导下，以不夸张之余也不失简约的魅力，尽情地演绎时尚潮流的变化。不浮夸，隆重但又不失RomanticStyle的内在元素，是YESO秉承的传统而富有现代感的清新风格。.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}.bigapic{width:750px;height:auto;}YESOOUTMASTER（户外大师）是在中国最具有影响力的休闲包品牌，以不夸张之余也不失简约的魅力，尽情地演绎时尚潮流的变化。YESO秉承传统而富有现代感的清新风格，是时尚品牌的先锋，适合上班、旅游等。时尚动感帅气十足！材质：牛津配皮包×1包装袋×1吊牌×1本产品全国联保，享受三包服务，质保期为：十五天质保服务承诺：京东商城向您保证所售商品均为正品行货，自带机打发票，与商品一起寄送。凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。京东商城还为您提供具有竞争力的商品价格和运费政策，请您放心购买！注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！, price=null, goodCommentsNumber=null, badCommentsNumber=null, comments=null]";
		String keyword = "Goods";		

		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();
	 
	
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			//建立内存索引对象
			directory = new RAMDirectory();	 
			
			//配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_36 , analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory , iwConfig);
			//写入索引
			Document doc = new Document();
			doc.add(new Field("ID", "10000", Field.Store.YES, Field.Index.NOT_ANALYZED));
			Field field =new Field(fieldName, text, Field.Store.YES, Field.Index.ANALYZED,TermVector.YES); //new Field(fieldName, text, Field.Store.YES, Field.Index.ANALYZED);
			System.out.print(field.isTermVectorStored());
			doc.add(field);
			iwriter.addDocument(doc);
			iwriter.close();
			
			
			//搜索过程**********************************
		    //实例化搜索器   
			ireader = IndexReader.open(directory);
			isearcher = new IndexSearcher(ireader);			
			
			//使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser(Version.LUCENE_36, fieldName, analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);
			
			//搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query , 5);
			System.out.println("命中：" + topDocs.totalHits);
			//输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			TermFreqVector termFreqVector;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				termFreqVector=ireader.getTermFreqVector(scoreDocs[i].doc, fieldName);
				String[] terms=termFreqVector.getTerms();
				int[] freqs=termFreqVector.getTermFrequencies();
				for(int j=0;j<terms.length;j++){
					System.out.print(terms[j]+":"+freqs[j]+"\n");
				}
				
				System.out.println("内容：" + targetDoc.toString());
			}			
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally{
			if(ireader != null){
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(directory != null){
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
