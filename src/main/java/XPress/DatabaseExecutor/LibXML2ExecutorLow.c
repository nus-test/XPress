#include <libxml/parser.h>
#include <libxml/xpath.h>
#include <string.h>
#include <stdlib.h>
#include "XTest_DatabaseExecutor_LibXML2ExecutorLow.h"

xmlChar* idAttrName = "id";

xmlChar*
getIdAttr (xmlNodePtr node) {
	int i = 0;
	xmlAttr* attribute = node->properties;
	while(attribute)
	{
		xmlChar* name = attribute -> name;
		if(!strcmp(name, idAttrName)) {
			return xmlNodeListGetString(node->doc, attribute->children, 1);
		}
		attribute = attribute -> next;
	}
	return NULL;
}


xmlDocPtr
getdoc (char *docname) {
	xmlDocPtr doc;
	doc = xmlParseFile(docname);

	if (doc == NULL ) {
		fprintf(stderr,"Document not parsed successfully. \n");
		return NULL;
	}

	return doc;
}

xmlXPathObjectPtr
getnodeset (xmlDocPtr doc, xmlChar *xpath){

	xmlXPathContextPtr context;
	xmlXPathObjectPtr result;

	context = xmlXPathNewContext(doc);
	if (context == NULL) {
		printf("Error in xmlXPathNewContext\n");
		return NULL;
	}
	result = xmlXPathEvalExpression(xpath, context);
	xmlXPathFreeContext(context);
	if (result == NULL) {
		printf("Error in xmlXPathEvalExpression\n");
		return NULL;
	}
	if(xmlXPathNodeSetIsEmpty(result->nodesetval)){
		xmlXPathFreeObject(result);
                printf("No result\n");
		return NULL;
	}
	return result;
}
JNIEXPORT jstring JNICALL Java_XTest_DatabaseExecutor_LibXML2ExecutorLow_execute
  (JNIEnv * env, jobject jobj, jstring jXMLDocumentPath, jstring jXPath) {
	const char *docname;
	xmlDocPtr doc;
	const char *xpath = (*env)->GetStringUTFChars(env, jXPath, NULL); // Pass XPath here
	xmlNodeSetPtr nodeset;
	xmlXPathObjectPtr result;
	int i;
	xmlChar *keyword;
	char *finalResult = malloc(10000 * 8);
	char space = ' ';
	finalResult[0] = '\0';
	docname = (*env)->GetStringUTFChars(env, jXMLDocumentPath, NULL); // Pass document Path here
	doc = getdoc(docname);
	result = getnodeset (doc, xpath);
	if (result) {
		nodeset = result->nodesetval;
		for (i=0; i < nodeset->nodeNr; i++) {
			keyword = getIdAttr(nodeset->nodeTab[i]); 
			 //xmlNodeListGetString(doc, nodeset->nodeTab[i]->xmlChildrenNode, 1);
			strcat(finalResult, keyword);
			strncat(finalResult, &space, 1);
			// printf("%s ", keyword);
			if(keyword != NULL)
				xmlFree(keyword);
		}
		// //printf("\n");
		xmlXPathFreeObject (result);
	}
	(*env)->ReleaseStringUTFChars(env, jXPath, xpath);
	(*env)->ReleaseStringUTFChars(env, jXMLDocumentPath, docname);
	jstring resultString = (*env)->NewStringUTF(env, finalResult);
	free(finalResult);
	xmlFreeDoc(doc);
	xmlCleanupParser();
	return resultString;
}