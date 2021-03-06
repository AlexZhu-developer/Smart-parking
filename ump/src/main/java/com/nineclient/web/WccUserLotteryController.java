package com.nineclient.web;
import com.nineclient.model.WccUserLottery;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/wccuserlotterys")
@Controller
@RooWebScaffold(path = "wccuserlotterys", formBackingObject = WccUserLottery.class)
public class WccUserLotteryController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WccUserLottery wccUserLottery, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccUserLottery);
            return "wccuserlotterys/create";
        }
        uiModel.asMap().clear();
        wccUserLottery.persist();
        return "redirect:/wccuserlotterys/" + encodeUrlPathSegment(wccUserLottery.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WccUserLottery());
        return "wccuserlotterys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("wccuserlottery", WccUserLottery.findWccUserLottery(id));
        uiModel.addAttribute("itemId", id);
        return "wccuserlotterys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("wccuserlotterys", WccUserLottery.findWccUserLotteryEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) WccUserLottery.countWccUserLotterys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("wccuserlotterys", WccUserLottery.findAllWccUserLotterys(sortFieldName, sortOrder));
        }
        return "wccuserlotterys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WccUserLottery wccUserLottery, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, wccUserLottery);
            return "wccuserlotterys/update";
        }
        uiModel.asMap().clear();
        wccUserLottery.merge();
        return "redirect:/wccuserlotterys/" + encodeUrlPathSegment(wccUserLottery.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WccUserLottery.findWccUserLottery(id));
        return "wccuserlotterys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WccUserLottery wccUserLottery = WccUserLottery.findWccUserLottery(id);
        wccUserLottery.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/wccuserlotterys";
    }

	void populateEditForm(Model uiModel, WccUserLottery wccUserLottery) {
        uiModel.addAttribute("wccUserLottery", wccUserLottery);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
