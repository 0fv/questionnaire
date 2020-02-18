package space.nyuki.questionnaire.service;

import lombok.SneakyThrows;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.nyuki.questionnaire.handler.ResultStringHandler;
import space.nyuki.questionnaire.pojo.*;
import space.nyuki.questionnaire.pojo.result.ResultCell;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ResultCollectionService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Value("${temp-file.dir}")
	private String dir;
	@Autowired
	private ResultStringHandler resultStringHandler;

	@Transactional
	public void saveData(SubmitResult submitResult) {
		String id = submitResult.getId();
		String fingerPrint = submitResult.getFingerPrint();
		String resultTable = "result:" + id;
		ResultCollection resultCollection = new ResultCollection();
		QuestionnaireEntity entity = questionnaireEntityService.getDataById(id);
		if (resultCheck(resultTable, fingerPrint) && entityCheck(entity, fingerPrint, resultCollection)) {
			resultCollection.setFingerPrint(fingerPrint);
			resultCollection.setEntityId(id);
			resultCollection.setSubmitDate(new Date());
			resultCollection.setSubmitResultGroups(submitResult.getSubmitResultGroups());
			mongoTemplate.save(resultCollection, resultTable);
			redisTemplate.opsForList().leftPush(resultTable, fingerPrint);
		}

	}

	private boolean resultCheck(String resultTable, String fingerPrint) {
		ResultCollection resultCollectionByFingerPrintId = getResultCollectionByFingerPrintId(fingerPrint, resultTable);
		return Objects.isNull(resultCollectionByFingerPrintId);
	}

	public ResultCollection getResultCollectionByFingerPrintId(String fingerPrint, String resultTable) {
		return mongoTemplate.findOne(Query.query(Criteria.where("finger_print").is(fingerPrint))
				, ResultCollection.class
				, resultTable);
	}

	private boolean entityCheck(QuestionnaireEntity entity, String memberId, ResultCollection resultCollection) {
		if (entity.getIsAnonymous() == 1) {
			List<Member> members = entity.getMembers();
			boolean flag = false;
			for (Member member : members) {
				if (member.getId().equals(memberId)) {
					flag = true;
					resultCollection.setName(member.getName());
					break;
				}
			}
			return flag;
		} else {
			return true;
		}
	}

	public List<ResultCollection> getData(String id) {
		return mongoTemplate.findAll(ResultCollection.class, "result:" + id);
	}

	@SneakyThrows
	public Resource exportXls(String id) {
		List<ResultCollection> resultCollections = getData(id);
		if (resultCollections.isEmpty()) {
			throw new EmptyFileException();
		}
		SXSSFWorkbook wb = new SXSSFWorkbook(2000);

		Sheet sheet = wb.createSheet("调查统计");
		sheet.setVerticallyCenter(true);
		ResultCollection resultCollection = resultCollections.get(0);
		setHeader(resultCollection, sheet);
		setContent(resultCollections, sheet);
		String filename = this.dir + File.separator + "编号" + id + "统计报告" + ".xlsx";
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		wb.write(fileOutputStream);
		wb.close();
		fileOutputStream.close();
		return new UrlResource(new File(filename).toURI().toASCIIString());
	}

	private void setContent(List<ResultCollection> resultCollections, Sheet sheet) {
		ResultCollection resultCollection = resultCollections.get(0);

		if (resultCollection.getName() == null) {
			setNoNameContent(resultCollections,sheet);
		}else{
			setHasNameContent(resultCollections,sheet);
		}
	}

	private void setHasNameContent(List<ResultCollection> resultCollections, Sheet sheet) {
		int rowIndex = 2;
		for (ResultCollection resultCollection : resultCollections) {
			Row row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(resultCollection.getFingerPrint());
			row.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultCollection.getSubmitDate()));
			List<SubmitResultGroup> submitResultGroups = resultCollection.getSubmitResultGroups();
			int colIndex = 2;
			for (SubmitResultGroup submitResultGroup : submitResultGroups) {
				for (ResultCell resultCell : submitResultGroup.getResultCells()) {
					String answer = resultStringHandler.getResultString(resultCell);
					row.createCell(colIndex).setCellValue(answer);
					colIndex++;
				}
			}
			rowIndex++;
		}
	}

	private void setNoNameContent(List<ResultCollection> resultCollections, Sheet sheet) {
		int rowIndex = 2;
		for (ResultCollection resultCollection : resultCollections) {
			Row row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(resultCollection.getFingerPrint());
			row.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultCollection.getSubmitDate()));
			List<SubmitResultGroup> submitResultGroups = resultCollection.getSubmitResultGroups();
			int colIndex = 2;
			for (SubmitResultGroup submitResultGroup : submitResultGroups) {
				for (ResultCell resultCell : submitResultGroup.getResultCells()) {
					String answer = resultStringHandler.getResultString(resultCell);
					row.createCell(colIndex).setCellValue(answer);
					colIndex++;
				}
			}
			rowIndex++;
		}
	}


	public void setHeader(ResultCollection resultCollection, Sheet sheet) {
		Row row = sheet.createRow(0);
		String[] hasMember = {"姓名", "识别码", "提交时间"};
		String[] noMember = {"识别码", "提交时间"};
		int rowIndex = 0;
		if (resultCollection.getName() == null) {
			for (; rowIndex < noMember.length; rowIndex++) {
				sheet.addMergedRegion(new CellRangeAddress(0, 1, rowIndex, rowIndex));
				row.createCell(rowIndex).setCellValue(noMember[rowIndex]);
			}
		} else {
			for (; rowIndex < hasMember.length; rowIndex++) {
				sheet.addMergedRegion(new CellRangeAddress(0, 1, rowIndex, rowIndex));
				row.createCell(rowIndex).setCellValue(hasMember[rowIndex]);
			}
		}
		int resultIndex = rowIndex;
		Row row1 = sheet.createRow(1);
		List<SubmitResultGroup> submitResultGroups = resultCollection.getSubmitResultGroups();
		for (SubmitResultGroup submitResultGroup : submitResultGroups) {
			String groupTitle = submitResultGroup.getGroupTitle();
			List<ResultCell> resultCells = submitResultGroup.getResultCells();
			int size = resultCells.size();
			sheet.addMergedRegion(new CellRangeAddress(0, 0, rowIndex, rowIndex + size - 1));
			row.createCell(rowIndex).setCellValue(groupTitle);
			rowIndex = rowIndex + size;
			for (ResultCell resultCell : resultCells) {
				row1.createCell(resultIndex).setCellValue(resultCell.getTitle());
				resultIndex++;
			}
		}
	}

}
