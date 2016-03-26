﻿/**
 * Pig script to test simple
 * joins between two relations.
 *
 * call this script like this:
 * ﻿pig -x local -param sample=input.vcf -param reference=reference.vcf -param output=res Pig_VCF_Join.pig
 *
 * @author: Clemens Banas
 * @date: March 2016
 */

REGISTER PigGene-1.0.jar;

--sample loading and pre-processing
sampleRel = LOAD '$sample' USING PigStorage('\t');

--reference loading and pre-processing
refRelIn = LOAD '$reference' USING PigStorage('\t') AS
            (REFchrom:chararray, REFpos:long, REFid:chararray, REFref:chararray, REFalt:chararray,
                REFqual:double, REFfilt:chararray, REFinfo:chararray);
referenceRel = FOREACH refRelIn GENERATE REFchrom, REFpos, REFinfo;

--join, result construction and output
joinedRel = JOIN referenceRel BY (REFchrom, REFpos) RIGHT OUTER, sampleRel BY ($0, $1);
projectedRel = FOREACH joinedRel GENERATE $3..$10, (($2 IS NULL) ? ' ' : $2), $11..;

STORE projectedRel INTO '$output';