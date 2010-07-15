#!/usr/bin/perl

use lib "lib";
use XML::LibXML;
use XML::Saxon::XSLT2;
use Data::Dumper;

open my $xslt, 'example2.xslt';
my $xml = XML::LibXML->new->parse_file('example2.xml');

my $transformation = XML::Saxon::XSLT2->new($xslt);
$transformation->parameters('bar' => [date=>'2010-02-28']);
print $transformation->transform_document($xml, 'xhtml')->toString . "\n";

print Dumper([$transformation->messages]) . "\n";