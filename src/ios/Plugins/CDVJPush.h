//
//  CDVJPush.h
//  XiaoYuan
//
//  Created by charles on 1/16/14.
//
//

#import <Cordova/CDVPlugin.h>
#import <Foundation/Foundation.h>

@interface CDVJPush : CDVPlugin

@property(nonatomic, copy) NSString *callbackID;

//- (void) register: (NSMutableArray *)arguments withDict:(NSMutableDictionary *) options;
- (void) register: (CDVInvokedUrlCommand*) command;
- (void) stop: (CDVInvokedUrlCommand*) command;
@end
