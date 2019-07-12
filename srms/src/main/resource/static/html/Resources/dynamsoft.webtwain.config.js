//
// Dynamsoft JavaScript Library for Basic Initiation of Dynamic Web TWAIN
// More info on DWT: http://www.dynamsoft.com/Products/WebTWAIN_Overview.aspx
//
// Copyright 2015, Dynamsoft Corporation 
// Author: Dynamsoft Team
// Version: 11.0
//
/// <reference path="dynamsoft.webtwain.initiate.js" />

var dwcId = "dwc_" + (Math.random()*1000000).toFixed(0);

$("#dwtcontrolContainer").html("<div id="+dwcId+"></div>");

var Dynamsoft = Dynamsoft || { WebTwainEnv: {} };

Dynamsoft.WebTwainEnv.AutoLoad = true;
///
Dynamsoft.WebTwainEnv.Containers = [{ContainerId:dwcId, Width:270, Height:350}];
///
Dynamsoft.WebTwainEnv.ProductKey = '6EEC3961A00D892CD8FB0C2B7AAA84922F0F588917DBAB5F554C17CE0767D0052F0F588917DBAB5FF3F58C785ED048522F0F588917DBAB5F62BED8BC13BC42252F0F588917DBAB5FB8D380DCF1BB11F12F0F588917DBAB5FAFE44B8C6F15E07250000000',
///
Dynamsoft.WebTwainEnv.Trial = true;
///
Dynamsoft.WebTwainEnv.ActiveXInstallWithCAB = true;
///
Dynamsoft.WebTwainEnv.Debug = false; // only for debugger output
///
// Dynamsoft.WebTwainEnv.ResourcesPath = 'Resources';

/// All callbacks are defined in the dynamsoft.webtwain.install.js file, you can customize them.

// Dynamsoft.WebTwainEnv.RegisterEvent('OnWebTwainReady', function(){
// 		// webtwain has been inited
// });

