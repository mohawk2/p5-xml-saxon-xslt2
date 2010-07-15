#!/usr/bin/perl

use lib "lib";
use Data::Dumper;
use XML::LibXML;
use XML::Saxon::XSLT2;
use LWP::Simple qw[get];

my $atom = get("http://intertwingly.net/blog/index.atom");

my $transformation = XML::Saxon::XSLT2->new(
	get('http://bblfish.net/work/atom-owl/2006-06-06/transform/atom2turtle.xsl'),
	'http://bblfish.net/work/atom-owl/2006-06-06/transform/atom2turtle.xsl'
	);
print $transformation->transform_document($atom)->toString . "\n";

