[![Contentstack](https://www.contentstack.com/docs/static/images/contentstack.png)](https://www.contentstack.com/)
## Android SDK for Contentstack

Contentstack is a headless CMS with an API-first approach. It is a CMS that developers can use to build powerful cross-platform applications in their favorite languages. Build your application frontend, and Contentstack will take care of the rest. [Read More](https://www.contentstack.com/).

Contentstack provides Android SDK to build application on top of Android. Given below is the detailed guide and helpful resources to get started with our Android SDK.



### Prerequisite

You will need one of the following: [Android Studio](https://developer.android.com/studio/install.html) or [Eclipse](https://eclipse.org/downloads/eclipse-packages/?show_instructions=TRUE).

### Setup and Installation

To add the Contentstack Android SDK to your existing project, perform the steps given below:

1. [Download the Android SDK](https://docs.contentstack.com/platforms/android/android_sdk_latest) and extract the ZIP file to your local disk.
2. Add references/dependencies using Eclipse/Android Studio:

##### Android Studio:

- Copy the 'Contentstack-x.x.x.jar' file into your project's libs folder.
- Add the dependency code into your 'build.gradle' file.

compile fileTree(dir: 'libs', include: ['*.jar'])

##### Eclipse:

- Copy the 'Contentstack-x.x.x-javadoc' folder and the 'Contentstack-x.x.x.jar' and 'Contentstack-x.x.x.jar.properties' files into your project's 'libs' folder.
- Open the 'Properties' window of the project. Select the 'Java Build Path' option on the left-hand side menu, click on the 'Libraries' tab, and add the JAR references there.
- Configure 'AndroidManifest.xml' with permissions and receivers using the following code:

<!-- Allows applications to connect network (Required) -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- Allows applications to access information about networks (Required) -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<receiver
android:name="com.builtio.contentstack.ConnectionStatus"
android:enabled="true" >
<intent-filter>
<action android:name="android.net.conn.CONNECTIVITY_CHANGE" >
</action>
</intent-filter>
</receiver>
<receiver
android:name="com.builtio.contentstack.ClearCache"
android:enabled="true">
<intent-filter>
<action android:name="StartContentStackClearingCache">
</action>
</intent-filter>
</receiver>


To initialize the SDK, specify application context, the API key, access token, and environment name of the stack as shown in the snippet given below:

Stack stack = Contentstack.stack(context, "siteApiKey", "accessToken", "enviroment_name");

Once you have initialized the SDK, you can query entries to fetch the required content.



### Key Concepts for using Contentstack

#### Stack

A stack is like a container that holds the content of your app. Learn more about [Stacks](https://www.contentstack.com/docs/guide/stack).

#### Content Type

Content type lets you define the structure or blueprint of a page or a section of your digital property. It is a form-like page that gives Content Managers an interface to input and upload content. [Read more](https://www.contentstack.com/docs/guide/content-types).

#### Entry

An entry is the actual piece of content created using one of the defined content types. Learn more about [Entries](https://www.contentstack.com/docs/guide/content-management#working-with-entries).

#### Asset

Assets refer to all the media files (images, videos, PDFs, audio files, and so on) uploaded to Contentstack. These files can be used in multiple entries. Read more about [Assets](https://www.contentstack.com/docs/guide/content-management#working-with-assets).

#### Environment

A publishing environment corresponds to one or more deployment servers or a content delivery destination where the entries need to be published. Learn how to work with [Environments](https://www.contentstack.com/docs/guide/environments).



### Contentstack Android SDK: 5-minute Quickstart

#### Initializing your SDK

To initialize the SDK, specify application context, the API key, access token, and environment name of the stack as shown in the snippet given below:

Stack stack = Contentstack.stack(context, "siteApiKey", "accessToken", "enviroment_name");

Once you have initialized the SDK, you can query entries to fetch the required content.



#### Querying content from your stack

To retrieve a single entry from a content type use the code snippet given below:

ContentType contentType = stack.contentType("content_type_uid");

Entry blogEntry = contentType.entry("entry_uid");blogEntry.fetch(new EntryResultCallBack() {@OverridepublicvoidonCompletion(ResponseType responseType, Error error) {
if (error == null) {
// Success block
} else {
// Error block
}}});

##### Get Multiple Entries

To retrieve multiple entries of a particular content type, use the code snippet given below:

//stack is an instance of Stack class

Query blogQuery = stack.contentType("content_type_uid").query();
blogQuery.find(new QueryResultsCallBack() {@OverridepublicvoidonCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
if(error == null){
//Success block
}else{
//Error block
}}});



### Advanced Queries

You can query for content types, entries, assets and more using our Android API Reference.

[Android API Reference Doc](https://www.contentstack.com/docs/platforms/android/api-reference/)

### Working with Images

We have introduced Image Delivery APIs that let you retrieve images and then manipulate and optimize them for your digital properties. It lets you perform a host of other actions such as crop, trim, resize, rotate, overlay, and so on.

For example, if you want to crop an image (with width as 300 and height as 400), you simply need to append query parameters at the end of the image URL, such as, https://images.contentstack.io/v3/assets/blteae40eb499811073/bltc5064f36b5855343/59e0c41ac0eddd140d5a8e3e/download?crop=300,400. There are several more parameters that you can use for your images.

[Read Image Delivery API documentation](https://www.contentstack.com/docs/apis/image-delivery-api/).

SDK functions for Image Delivery API coming soon.



### Helpful Links

- [Contentstack Website](https://www.contentstack.com)
- [Official Documentation](https://contentstack.com/docs)
- [Content Delivery API Docs](https://contentstack.com/docs/apis/content-delivery-api/)

### The MIT License (MIT)

Copyright © 2012-2017 [Built.io](https://www.built.io/). All Rights Reserved

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. https://github.com/rjeschke/txtmark
