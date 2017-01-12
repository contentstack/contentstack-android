[![Built.io Contentstack](https://contentstackdocs.built.io/static/images/logo.png)](https://www.built.io/products/contentstack/overview)

# Android SDK for Built.io Contentstack

Android client for [Built.io Contentstack](https://www.built.io/products/contentstack/overview)  - the API-first CMS for your app. This SDK interacts only with the [Content Delivery Rest API](https://contentstackdocs.built.io/developer/restapi).

Contentstack is the CMS without the BS. With this headless cms, developers can build powerful cross-platform applications using their favorite front-end javascript frameworks and Android/iOS clients. 

You build your front-end and we will take care of delivering content through APIs, optimized for each destination. - [more here](https://www.built.io/products/contentstack/overview) 

### Prerequisite
 - Android studio or Ecilipse 

### Setup and Installation
To use this SDK on android platform, you will have to install the SDK according to the steps given below.

- Download the Contentstack android sdk.

- The zip file contains the Built.io Contentstack SDK. Extract the Contentstack zip file at the desired location.

- #####Android Studio
	- Copy Contentstack-x.x.x.jar to your project's libs folder.

	- Add dependency code in your build.gradle file.
	
			compile fileTree(dir: 'libs', include: ['*.jar'])


- #####Eclipse
	- Copy Contentstack-x.x.x-javadoc, Contentstack-x.x.x.jar, Contentstack-x.x.x.jar.properties to your project's libs folder.

	- Add jar refrences in the projects propeties --> Java Build Path --> Libraries tab.
			
- Configure android manifest with permissions.
 
			<uses-permission android:name="android.permission.INTERNET" />
 			<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

To start using the SDK in your application, you will need to initialize the stack by providing the required keys and values associated with them.

		Stack stack = Contentstack.stack(context, API_KEY, ACCESS_TOKEN, ENVIRONMENT);


### Key Concepts for using Contentstack

##### Stack
A stack is like a container that holds the content of your app. Learn more about creating stacks. [watch videos tutorials with documentation](https://contentstackdocs.built.io/developer/android/quickstart)

##### Content Type

A content type is the structure of a section with one or more fields within it. It is a form-like page that gives Content Managers an interface to input and upload content. 

##### Entry

An entry is the actual piece of content created using one of the defined content types. 

##### Asset

Assets refer to all the media files (images, videos, PDFs, audio files, and so on) uploaded to Built.io Contentstack. These files can be used in multiple entries.  

##### Environment

A publishing environment corresponds to one or more deployment servers or a content delivery destination where the entries need to be published. 


### 5 minute Quickstart

#### Initializing your Stack client
To initialize a Stack client, you need to provide the required keys and values associated with them:

	Stack stack = Contentstack.stack(context, API_KEY, ACCESS_TOKEN, ENVIRONMENT);

To get the api credentials mentioned above, you need to log into your Contentstack account and then in your top panel navigation, go to Settings -> Stack to view both your `API Key` and your `Access Token`


The `stack` object that is returned a Built.io Contentstack client object, which can be used to initialize different modules and make queries against our [Content Delivery API](https://contentstackdocs.built.io/rest/api/content-delivery-api/). The initialization process for each module is explained below.


#### Querying content from your stack

Let us take an example where we try to obtain all entries of the Content Type my_content_type.

	Query query = stack.contentType(CONTENT_TYPE_UID).query();  
	
Let us take another example where we try to obtain only a specific entry from the Content Type `my_content_type`.

	Entry entry = stack.contentType(my_content_type).entry(ENTRY_UID);


### More Usage

You can query for content types, entries, assets and more using our completely documented api. Here are some useful examples:

Get a specific `ContentType` by `content_type_uid`

	ContentType blogType = stack.contentType("blog");;

Get a specific `Entry` by `entry_uid`

	Entry blogEntry = stack.contentType("blog").entry("blt1234567890abcef");

Fetch all entries of requested `content_type` by quering.

	Query query = stack.contentType("blog").query();		
### Compile and Create

Run gradle `command` in terminal to create .jar.

	./gradlew clean createJar

Outputs can be found in app/build/libs/

### How Do I Contribute?
Contentstack team want to make contributing to this project as easy as possible. Please find a Contribution Guidelines to contribute.

### Dependency library
Android Contentstack SDK contains following dependency:

- [Volley](https://github.com/mcxiaoke/android-volley)
- [okHttp](https://github.com/square/okhttp)
- [txtmark](https://github.com/rjeschke/txtmark)


### Next steps

- [Online Query Guide](https://contentstackdocs.built.io/developer/android/query-guide)
- [Online API Reference (android examples coming soon)](https://contentstackdocs.built.io/android/api/)

### Links
 - [Website](https://www.built.io/products/contentstack/overview)
 - [Official Documentation](http://contentstackdocs.built.io/developer/android/quickstart)
 - [Content Delivery Rest API](https://contentstackdocs.built.io/developer/restapi)

### The MIT License (MIT)
Copyright © 2012-2016 [Built.io](https://www.built.io/). All Rights Reserved

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWAREhttps://github.com/rjeschke/txtmark.