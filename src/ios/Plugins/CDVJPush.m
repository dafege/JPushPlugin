//
//  CDVJPush.m
//  XiaoYuan
//
//  Created by charles on 1/16/14.
//
// 333333

#import "CDVJPush.h"
#import "APService.h"

@implementation CDVJPush
@synthesize callbackID;
- (void) register:(CDVInvokedUrlCommand*) command
{
    //self.callbackID = [arguments pop];
    
    NSString *stringObtainedFormJavascript = [command.arguments objectAtIndex:0];
    NSLog(@"------- JS pass parameter is: %@ -------", stringObtainedFormJavascript);
    
    [APService registerForRemoteNotificationTypes:(UIRemoteNotificationTypeBadge |
                                                   UIRemoteNotificationTypeSound |
                                                   UIRemoteNotificationTypeAlert)];
    
    [APService setAlias:stringObtainedFormJavascript callbackSelector:@selector(aliasCallback:tags:alias:) object:self];
    
}

- (void)aliasCallback:(int)iResCode tags:(NSSet*)tags alias:(NSString*)alias {
    NSLog(@"rescode: %d, \nalias: %@\n", iResCode, alias);
    
    NSMutableString *stringReturn = [NSMutableString stringWithString:@"StringReceived"];
    [stringReturn appendString:alias];
    
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                     messageAsString:stringReturn];
    [self writeJavascript:[pluginResult toSuccessCallbackString:callbackID]];
}

- (void) stop : (CDVInvokedUrlCommand*) command
{
    [[UIApplication sharedApplication] unregisterForRemoteNotifications];
}

@end
